package src.librarysystem;

import com.beust.ah.A;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import  java.util.HashMap;

public class SearchBookController extends BaseController {
    @FXML
    public Button apiModeButton;
    @FXML
    public Button libraryModeButton;
    @FXML
    public TextField bookQuery;
    @FXML
    public FlowPane flowPane;
    @FXML
    public ComboBox filterModeButton;

    private boolean apiMode = false;
    private String query = "";

    private ArrayList<Book> currentApiBook = new ArrayList<>();
    private ArrayList<Book> currentLibraryBook = DBInfo.getBookList("ALL", "ALL", "ALL");

    // 0 : title
    // 1 : author
    // 2 : category
    @FXML
    private int filterMode = 0;

    // HashMap để lưu cache ảnh
    private HashMap<String, Image> imageCache = new HashMap<>();

    private void update() {
        bookQuery.clear();
        String activeMode = "-fx-background-color: #fff; -fx-cursor: hand;";
        String inActiveMode = "-fx-background-color: transparent; -fx-cursor: hand;";
        apiModeButton.setStyle(apiMode ? activeMode : inActiveMode);
        libraryModeButton.setStyle(!apiMode ? activeMode : inActiveMode);
    }

    public void initialize() {
        apiMode = MainGUI.apiSearchMode;
        query = MainGUI.previousQuery;

        bookQuery.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                searchQuery(bookQuery.getText());
            }
        });
        update();

        filterModeButton.getItems().addAll("Title", "Author", "Category");
        filterModeButton.setValue("Title");

        filterModeButton.setOnAction(event -> {
            String selectedValue = String.valueOf(filterModeButton.getValue()); // No casting required if ComboBox<String>
            switch (selectedValue) {
                case "Title":
                    filterMode = 0;
                    break;
                case "Author":
                    filterMode = 1;
                    break;
                case "Category":
                    filterMode = 2;
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + selectedValue);
            }
        });
        searchQuery(query);
        System.out.println(apiMode + " " + query);
        if(apiMode) {
            displayBooks(currentApiBook);
        }
        else {
            displayBooks(currentLibraryBook);
        }
    }

    private void searchQuery(String query) {
        if (query.isEmpty() || query == null) {
            if (apiMode) {
                return;
            } else {
                currentLibraryBook = DBInfo.getBookList("ALL", "ALL", "ALL");
            }
        }

        // Hiển thị SearchingProgressing
        VBox progressBox = SearchingProgressing.getInstance().createProgressingVBox();
        flowPane.getChildren().clear();
        flowPane.getChildren().add(progressBox);

        // Tạo Task để chạy tìm kiếm trong nền
        Task<Void> searchTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (apiMode) {
                    switch (filterMode) {
                        case 0:
                            currentApiBook = BookServices.getInstance().searchBooksByTitle(query);
                            break;
                        case 1:
                            currentApiBook = BookServices.getInstance().searchBooksByAuthor(query);
                            break;
                        case 2:
                            currentApiBook = BookServices.getInstance().searchBooksByCategory(query);
                            break;
                    }
                } else {
                    switch (filterMode) {
                        case 0:
                            currentLibraryBook = Filter.getInstance().getBookByTitleSubstr(query);
                            break;
                        case 1:
                            currentLibraryBook = Filter.getInstance().getBookByAuthorSubstr(query);
                            break;
                        case 2:
                            currentLibraryBook = Filter.getInstance().getBookByCategorySubstr(query);
                            break;
                    }
                }
                return null;
            }

            @Override
            protected void succeeded() {
                // Ẩn SearchingProgressing khi tìm kiếm hoàn tất
                flowPane.getChildren().clear();
                if (apiMode) {
                    displayBooks(currentApiBook);
                } else {
                    displayBooks(currentLibraryBook);
                }
                MainGUI.setPreviousStage(apiMode, query);
            }

            @Override
            protected void failed() {
                // Ẩn SearchingProgressing khi tìm kiếm thất bại
                flowPane.getChildren().clear();
                System.out.println("Tìm kiếm thất bại");
            }
        };

        // Chạy Task trong luồng nền
        new Thread(searchTask).start();
    }


    @FXML
    public void searchBook(ActionEvent actionEvent) {
        searchQuery(bookQuery.getText());
    }

    public void displayBooks(ArrayList<Book> result) {
        flowPane.getChildren().clear(); // Xóa các phần tử cũ
        if (result == null) return;

        // Cấu hình FlowPane
        flowPane.setPrefWidth(1080);
        flowPane.setHgap(60); // Khoảng cách ngang giữa các thẻ
        flowPane.setVgap(20); // Khoảng cách dọc giữa các hàng
        flowPane.setStyle("-fx-background-color: transparent; -fx-padding: 0 0 0 60px;");

        for (Book book : result) {
            Pane bookPane = new Pane();
            bookPane.setPrefSize(460, 192);
            bookPane.setStyle("-fx-background-color: #fff; -fx-background-radius: 30px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 0, 4);");
            ImageView imageView = new ImageView();
            imageView.setFitHeight(192);
            imageView.setFitWidth(144);

            String imageUrl;

            if (book.getThumbnail() != null && !book.getThumbnail().isEmpty()) {
                // Use the thumbnail URL from the API
                imageUrl = book.getThumbnail();
            } else {
                // Use the default local image for missing thumbnails
                imageUrl = getClass().getResource("/images/unnamed.jpg").toExternalForm();
            }

            // Check if the image is already in the cache
            if (imageCache.containsKey(imageUrl)) {
                imageView.setImage(imageCache.get(imageUrl)); // Use cached image
            } else {
                try {
                    // Load the image (supports both local and remote URLs)
                    Image image = new Image(imageUrl, true);
                    imageCache.put(imageUrl, image); // Cache the image
                    imageView.setImage(image);
                } catch (Exception e) {
                    // Fallback in case of an error (e.g., invalid URL)
                    System.err.println("Failed to load image: " + imageUrl);
                    imageView.setImage(new Image(getClass().getResource("/images/unnamed.jpg").toExternalForm()));
                }
            }


            bookPane.getChildren().add(imageView);

            // Tạo Label cho tiêu đề
            Label titleLabel = new Label(book.getTitle());
            titleLabel.setLayoutX(160); // Căn lề phải hơn
            titleLabel.setLayoutY(22);
            titleLabel.setPrefSize(250, 17);
            titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
            bookPane.getChildren().add(titleLabel);

            // Tạo Label cho tác giả
            Label authorLabel = new Label(book.getAuthors());
            authorLabel.setLayoutX(160); // Căn lề phải hơn
            authorLabel.setLayoutY(49);
            authorLabel.setPrefSize(250, 17);
            authorLabel.setStyle("-fx-text-fill: #606060; -fx-font-size: 10px;");
            bookPane.getChildren().add(authorLabel);

            // Tạo nhãn danh mục
            Label categoryLabel = new Label(book.getCategory() != null ? book.getCategory() : "Không phân loại");
            categoryLabel.setLayoutX(160); // Căn lề phải hơn
            categoryLabel.setLayoutY(119);
            categoryLabel.setStyle("-fx-background-color: rgba(0, 128, 255, 0.1); -fx-text-fill: #0078D4; -fx-padding: 2 5; -fx-font-size: 10px;");
            bookPane.getChildren().add(categoryLabel);

            // Thêm đánh giá bằng font awesome icon
            Label ratingLabel = new Label("\uf005 \uf005 \uf005 \uf005 \uf005"); // Unicode của icon STAR_ALT
            ratingLabel.setLayoutX(160); // Căn lề phải hơn
            ratingLabel.setLayoutY(85);
            ratingLabel.setStyle("-fx-font-family: 'FontAwesome'; -fx-text-fill: #FFD700; -fx-font-size: 12px;");
            bookPane.getChildren().add(ratingLabel);

            // Tạo Button
            Button detailButton = new Button("Xem chi tiết");
            detailButton.setLayoutX(189);
            detailButton.setLayoutY(153);
            detailButton.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-border-color: #0078D4; -fx-border-radius: 5px; -fx-text-fill: #0078D4;");
            detailButton.setOnAction(event -> {
                returnDetailBook(book, apiMode);
            });
            bookPane.getChildren().add(detailButton);

            // Thêm Pane vào FlowPane
            flowPane.getChildren().add(bookPane);
        }
    }

    @FXML
    public void apiMode(ActionEvent actionEvent) {
        System.out.println("api mode");
        if (!apiMode) {
            apiMode = true;
        }
        BookViewDetailController.setApiMode(apiMode);
        currentApiBook = null;
        update();
        displayBooks(currentApiBook);
        MainGUI.setPreviousStage(apiMode, query);
    }

    @FXML
    public void libraryMode(ActionEvent actionEvent) {
        System.out.println("library mode");
        if (apiMode) {
            apiMode = false;
        }
        BookViewDetailController.setApiMode(apiMode);
        currentLibraryBook = DBInfo.getBookList("ALL", "ALL", "ALL");
        update();
        query = "";
        displayBooks(currentLibraryBook);
        MainGUI.setPreviousStage(apiMode, query);
    }
}