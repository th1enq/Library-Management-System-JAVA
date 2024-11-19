package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import  java.util.HashMap;

public class SearchBookController {
    @FXML
    public Button apiModeButton;
    @FXML
    public Button libraryModeButton;
    @FXML
    public TextField bookQuery;
    @FXML
    public FlowPane flowPane;

    private boolean apiMode = false;

    private ArrayList<Book> currentApiBook;
    private ArrayList<Book> currentLibraryBook;

    private MainGUI mainGUI;

    // HashMap để lưu cache ảnh
    private HashMap<String, Image> imageCache = new HashMap<>();

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    private void update() {
        String activeMode = "-fx-background-color: #fff; -fx-cursor: hand;";
        String inActiveMode = "-fx-background-color: transparent; -fx-cursor: hand;";
        apiModeButton.setStyle(apiMode ? activeMode : inActiveMode);
        libraryModeButton.setStyle(!apiMode ? activeMode : inActiveMode);
    }

    public void initialize() {
        bookQuery.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                searchQuery();
            }
        });
        apiMode = false;
        update();
        currentApiBook = null;
        currentLibraryBook = DBInfo.getBookList("ALL", "ALL", "ALL");
        displayBooks(currentLibraryBook);
    }

    private void searchQuery() {
        String query = bookQuery.getText();
        currentApiBook = BookServices.searchBooks(query);
        currentLibraryBook = DBInfo.getBookList("ALL", "ALL", "ALL");
        displayBooks(apiMode ? currentApiBook : currentLibraryBook);
    }

    @FXML
    public void searchBook(ActionEvent actionEvent) {
        System.out.println("search");
        searchQuery();
    }

    @FXML
    private ScrollPane scrollPane;

    public void displayBooks(ArrayList<Book> result) {
        flowPane.getChildren().clear(); // Xóa các phần tử cũ
        if (result == null) return;

        // Cấu hình FlowPane
        flowPane.setPrefWidth(1099);
        flowPane.setHgap(60); // Khoảng cách ngang giữa các thẻ
        flowPane.setVgap(20); // Khoảng cách dọc giữa các hàng
        flowPane.setStyle("-fx-background-color: transparent; -fx-padding: 0 0 0 60px;");

        for (Book book : result) {
            Pane bookPane = new Pane();
            bookPane.setPrefSize(460, 192);
            bookPane.setStyle("-fx-background-color: #fff; -fx-background-radius: 30px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 0, 4);");

            if (apiMode) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(192);
                imageView.setFitWidth(144);

                String imageUrl = apiMode && book.getThumbnail() != null && !book.getThumbnail().isEmpty()
                        ? book.getThumbnail()
                        : "/images/bookTest1.jpg";

                // Kiểm tra cache ảnh
                if (imageCache.containsKey(imageUrl)) {
                    imageView.setImage(imageCache.get(imageUrl)); // Sử dụng ảnh đã cache
                } else {
                    Image image = new Image(imageUrl, true);
                    imageCache.put(imageUrl, image); // Lưu ảnh vào cache
                    imageView.setImage(image);
                }

                bookPane.getChildren().add(imageView);
            }

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
                mainGUI.returnDetailBook(book, apiMode);
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
        update();
        displayBooks(currentApiBook);
    }

    @FXML
    public void libraryMode(ActionEvent actionEvent) {
        System.out.println("library mode");
        if (apiMode) {
            apiMode = false;
        }
        update();
        displayBooks(currentLibraryBook);
    }
}