package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookViewDetailController {
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
    }

    public Button getReturnSearchBook() {
        return returnSearchBook;
    }

    @FXML
    public void rentBook(ActionEvent actionEvent) {
        MainGUI.currentUser.muonSach(currentBook);
        System.out.println("Yêu cầu mượn sách đã được gửi đến admin, hãy chờ admin phê duyệt");
        for(Pair<Book, MyDateTime> x : MainGUI.currentUser.getRentBook()) {
            System.out.println(x.getKey().toString() + " " + x.getValue().toString());
        }
    }

    @FXML
    public void editBook(ActionEvent actionEvent) {
        if(apiMode) {
            System.out.println("Không thể chỉnh sửa sách từ API");
            return;
        }
        editMode = true;
        update();
    }

    @FXML
    public void removeBook(ActionEvent actionEvent) {
        System.out.println(currentBook.toString());
        DBInfo.deleteBook(currentBook);
    }

    public void saveEditBook(ActionEvent actionEvent) {
        detailTitle.setText(editTitle.getText());
        detailAuthor.setText(editAuthor.getText());
        detailDescription.setText(editDescription.getText());
        detailPushlisher.setText(editPushlisher.getText());
        detailLanguage.setText(editLanguage.getText());
        detailPaperback.setText(editPaperback.getText());

        DBInfo.editBook(currentBook, detailTitle.getText(), detailAuthor.getText(), detailPushlisher.getText(), currentBook.getThumbnail(), detailDescription.getText(), detailPaperback.getText(), detailLanguage.getText());

        editMode = false;
        update();
    }

    @FXML
    public void addBook(ActionEvent actionEvent) {
        DBInfo.addBook(currentBook);
    }
}
