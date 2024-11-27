package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BookViewDetailController extends BaseController {
    @FXML
    public ImageView qrImage;
    @FXML
    public TextField editTitle;
    @FXML
    public TextField editAuthor;
    @FXML
    public TextArea editDescription;
    @FXML
    public TextField editPushlisher;
    @FXML
    public TextField editLanguage;
    @FXML
    public TextField editPaperback;
    @FXML
    public Button saveButton;
    @FXML
    public Button editButton;
    @FXML
    public Button addBookButton;
    @FXML
    public Button rentBookButton;
    @FXML
    public CheckBox confirmBorrowed;
    @FXML
    public Button submitBorrow;
    @FXML
    public Button cancelBorrow;
    @FXML
    public Pane borrowPane;
    @FXML
    public Button submitDelete;
    @FXML
    public Button cancelDelete;
    @FXML
    public Pane deletePane;
    @FXML
    public Pane tempPane;
    @FXML
    public Pane containerPane;
    public Button seeComments;
    public Pane commentTempPane;
    public TextArea commentContent;
    public Button submitComment;
    public Label currentWord;
    @FXML
    public Button cancelComment;
    public Pane commentPane;
    @FXML
    public Pane flowPane;
    @FXML
    public ScrollPane commentPaneUser;
    @FXML
    private Label detailTitle;
    @FXML
    private Label detailAuthor;
    @FXML
    private Label detailDescription;
    @FXML
    private Label detailPushlisher;
    @FXML
    private Label detailLanguage;
    @FXML
    private Label detailPaperback;
    @FXML
    private ImageView detailImage;
    @FXML
    private Button returnSearchBook;

    private boolean editMode = false;

    public static boolean apiMode = false;

    private Book currentBook = new Book();

    public static void setApiMode(boolean apiMode) {
        BookViewDetailController.apiMode = apiMode;
    }


    private int currentRate = 5;
    private Button[] stars = new Button[5];
    private ArrayList<Comment> commentList;

    void update() {
        editTitle.setText(detailTitle.getText());
        editAuthor.setText(detailAuthor.getText());
        editDescription.setText(detailDescription.getText());
        editPushlisher.setText(detailPushlisher.getText());
        editLanguage.setText(detailLanguage.getText());
        editPaperback.setText(detailPaperback.getText());

        editTitle.setVisible(editMode);
        editAuthor.setVisible(editMode);
        editDescription.setVisible(editMode);
        editPushlisher.setVisible(editMode);
        editLanguage.setVisible(editMode);
        editPaperback.setVisible(editMode);

        detailTitle.setVisible(!editMode);
        detailAuthor.setVisible(!editMode);
        detailDescription.setVisible(!editMode);
        detailPushlisher.setVisible(!editMode);
        detailLanguage.setVisible(!editMode);
        detailPaperback.setVisible(!editMode);

        saveButton.setVisible(editMode);
        editButton.setVisible(!editMode);
    }

    public void initialize(Book book) {
        if (book != null) {
            currentBook = book;
            // Set text details with null checks
            detailTitle.setText(book.getTitle() != null ? book.getTitle() : "Unknown Title");
            detailDescription.setText(book.getDescription() != null ? book.getDescription() : "No description available.");
            detailAuthor.setText(book.getAuthors() != null ? book.getAuthors() : "Unknown Author");
            detailPushlisher.setText(book.getPublisher() != null ? book.getPublisher() : "Unknown Publisher");
            detailLanguage.setText(book.getLanguage() != null ? book.getLanguage() : "Unknown Language");
            detailPaperback.setText(book.getNumPage() != null ? book.getNumPage() : "N/A");

            // Load the image and handle missing images
            String imagePath = book.getThumbnail(); // Assuming this method gets the image path

            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Attempt to load the image from the provided path or URL
                    detailImage.setImage(new Image(imagePath, true));
                } catch (Exception e) {
                    // If the image fails to load, set the default image
                    detailImage.setImage(new Image(getClass().getResource("/images/unnamed.jpg").toExternalForm()));
                }
            } else {
                // Use the default image if no thumbnail is provided
                detailImage.setImage(new Image(getClass().getResource("/images/unnamed.jpg").toExternalForm()));
            }

            qrImage.setImage(BookServices.getInstance().generateQRCode(book.getBuyLink()));
        } else {
            // If book is null, set default messages for each label
            detailTitle.setText("No Book Selected");
            detailDescription.setText("No description available.");
            detailAuthor.setText("Unknown Author");
            detailPushlisher.setText("Unknown Publisher");
            detailLanguage.setText("Unknown Language");
            detailPaperback.setText("N/A");
            // Optionally set a default image
            detailImage.setImage(new Image("resources/images/unnamed.jpg"));
        }

        rentBookButton.setVisible(!apiMode);
        addBookButton.setVisible(apiMode);

        submitBorrow.setOnAction(event -> {
            boolean confirm = confirmBorrowed.isSelected();
            if(confirm) {;
                MainGUI.currentUser.muonSach(currentBook);
                sendNotification(1000, MainGUI.currentUser.getId(), "Yêu cầu mượn sách đã được gửi. Vui lòng chờ xác nhận từ admin");
                containerPane.setEffect(null);
                borrowPane.setVisible(false);
                tempPane.setVisible(false);
            }
        });

        cancelBorrow.setOnAction(event -> {
            containerPane.setEffect(null);
            borrowPane.setVisible(false);
            tempPane.setVisible(false);
        });

        submitDelete.setOnAction(event -> {
            DBInfo.deleteBook(currentBook);
            sendNotification(1000, MainGUI.currentUser.getId(), "Xóa sách thành công !!!");
            containerPane.setEffect(null);
            deletePane.setVisible(false);
            tempPane.setVisible(false);
        });

        cancelDelete.setOnAction(event -> {
            containerPane.setEffect(null);
            deletePane.setVisible(false);
            tempPane.setVisible(false);
        });

        seeComments.setOnAction(event -> {
            containerPane.setEffect(new GaussianBlur(10));
            commentTempPane.setVisible(true);
        });

        cancelComment.setOnAction(event -> {
            containerPane.setEffect(null);
            commentTempPane.setVisible(false);
        });

        int rootLayoutX = 31;
        int rootLayoutY = 200;

        // Khởi tạo 5 ngôi sao và thêm vào commentPane
        for (int i = 0; i < 5; i++) {
            FontAwesomeIcon starIcon = new FontAwesomeIcon();
            starIcon.setGlyphName("STAR");
            starIcon.setSize("1.5em");
            starIcon.setFill(Color.valueOf("#FFD700"));

            stars[i] = new Button();
            stars[i].setGraphic(starIcon);
            stars[i].setTranslateX(rootLayoutX + i * 30); // Khoảng cách giữa các ngôi sao
            stars[i].setTranslateY(rootLayoutY);
            stars[i].setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

//            // Gắn sự kiện click vào từng ngôi sao
            final int index = i;
            stars[i].setOnMouseClicked(event -> onStarClick(index));

            commentPane.getChildren().add(stars[i]);
        }
        int maxLength = 144;

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= maxLength) {
                return change;  // Cho phép thay đổi nếu không vượt quá giới hạn
            } else {
                return null;  // Nếu quá 144 ký tự, không cho phép thay đổi
            }
        });

        commentContent.setTextFormatter(textFormatter);
        currentWord.textProperty().bind(Bindings.format("%d/144", commentContent.textProperty().length()));

        submitComment.setOnAction(event -> {
            String commentText = commentContent.getText();
            if(commentText != null && !commentText.isEmpty()) {
                MainGUI.currentUser.addComment(currentBook, commentText, currentRate);
                commentContent.clear();
                createComment();
            }
        });
        createComment();
    }

    private void createComment() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        commentList = DBInfo.getCommentList(currentBook);
        commentList.sort(Comparator.comparing(Comment::getTime).reversed());
        for(Comment comment : commentList) {
            Pane currentComment = createCommentPane(comment);
            vbox.getChildren().add(currentComment);
        }
        commentPaneUser.setContent(vbox);
    }

    private Pane createCommentPane(Comment comment) {
        // Layout chung cho mỗi comment
        Pane pane = new Pane();
        pane.setPrefSize(430, 124);  // Cài đặt kích thước của Pane
        pane.setLayoutX(7);
        pane.setLayoutY(14);

        // Tạo Label cho tên người dùng
        Label usernameLabel = new Label(comment.getUsername());
        usernameLabel.setFont(new Font("System Bold", 14));
        usernameLabel.setLayoutY(0);

        // Tạo TextArea cho nội dung bình luận
        Label commentTextArea = new Label(comment.getContent());
        commentTextArea.setFont(new Font(14));
        commentTextArea.setLayoutY(33);
        commentTextArea.setPrefHeight(73);
        commentTextArea.setPrefWidth(460);
        commentTextArea.setStyle("-fx-background-color: transparent;");
        commentTextArea.setWrapText(true);

        // Tạo Label cho thời gian
        Label dateLabel = new Label(comment.getTime().toString());
        dateLabel.setLayoutY(102);
        dateLabel.setTextFill(Color.web("#868686"));
        dateLabel.setFont(Font.font(14));

        // Tạo đường kẻ (Line)
        Line line = new Line();
        line.setStartX(-90.99);
        line.setStartY(2);
        line.setEndX(369.0);
        line.setEndY(2);
        line.setLayoutX(91);
        line.setLayoutY(120);

        int bookRating = comment.getRate();

        // Tạo các ngôi sao
        HBox stars = new HBox(5);  // Dùng HBox để sắp xếp các ngôi sao theo chiều ngang
        stars.setLayoutX(175);
        stars.setLayoutY(2);

        for (int i = 0; i < 5; i++) {
            FontAwesomeIcon star = new FontAwesomeIcon();
            star.setSize("1.2em");
            star.setFill(Color.GOLD);
            if(i < bookRating) {
                star.setGlyphName("STAR");
            }
            else {
                star.setGlyphName("STAR_ALT");
            }
            stars.getChildren().add(star);
        }

        pane.setStyle("fx-background-color: transparent");

        // Thêm các phần tử vào Pane
        pane.getChildren().addAll(usernameLabel, commentTextArea, dateLabel, line, stars);

        return pane;
    }

    private void onStarClick(int clickedIndex) {
        currentRate = clickedIndex + 1;
        // Cập nhật màu của các ngôi sao dựa trên ngôi sao người dùng click
        for (int i = 0; i <= clickedIndex; i++) {
            // Đổi tên biểu tượng thành STAR (ngôi sao vàng)
            FontAwesomeIcon starIcon = (FontAwesomeIcon) stars[i].getGraphic();
            starIcon.setGlyphName("STAR");
        }
        for (int i = clickedIndex + 1; i < 5; i++) {
            // Đổi tên biểu tượng thành STAR_ALT (ngôi sao rỗng)
            FontAwesomeIcon starIcon = (FontAwesomeIcon) stars[i].getGraphic();
            starIcon.setGlyphName("STAR_ALT");
        }
    }

    public Button getReturnSearchBook() {
        return returnSearchBook;
    }

    @FXML
    public void rentBook(ActionEvent actionEvent) {
        GaussianBlur blurEffect = new GaussianBlur(10);
        containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
        tempPane.setVisible(true);
        borrowPane.setVisible(true);
    }

    @FXML
    public void editBook(ActionEvent actionEvent) {
        if(apiMode) {
            sendNotification(1000, MainGUI.currentUser.getId(), "Bạn không thể chỉnh sửa sách từ thư viện online !!!");
            return;
        }
        editMode = true;
        update();
    }

    @FXML
    public void removeBook(ActionEvent actionEvent) {
        if(apiMode == true) {
            sendNotification(1000, MainGUI.currentUser.getId(), "Không thể xóa sách từ thư viện online !!!");
            return;
        }
        GaussianBlur blurEffect = new GaussianBlur(10);
        containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
        tempPane.setVisible(true);
        deletePane.setVisible(true);
    }

    public void saveEditBook(ActionEvent actionEvent) {
        detailTitle.setText(editTitle.getText());
        detailAuthor.setText(editAuthor.getText());
        detailDescription.setText(editDescription.getText());
        detailPushlisher.setText(editPushlisher.getText());
        detailLanguage.setText(editLanguage.getText());
        detailPaperback.setText(editPaperback.getText());

        DBInfo.editBook(currentBook, detailTitle.getText(), detailAuthor.getText(), detailPushlisher.getText(), currentBook.getThumbnail(), detailDescription.getText(), detailPaperback.getText(), detailLanguage.getText());

        sendNotification(1000, MainGUI.currentUser.getId(), "Chỉnh sửa sách thành công !!!");

        editMode = false;
        update();
    }

    @FXML
    public void addBook(ActionEvent actionEvent) {
        sendNotification(1000, MainGUI.currentUser.getId(), "Thêm thành công vào thư viện !!!");
        DBInfo.addBook(currentBook);
    }
}
