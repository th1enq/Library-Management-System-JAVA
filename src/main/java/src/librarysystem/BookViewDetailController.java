package src.librarysystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookViewDetailController {
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

    public void initialize(Book book) {
        if (book != null) {
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
                // Load image from the URL or file path
                detailImage.setImage(new Image(imagePath));
            } else {
                // Set a default image if no thumbnail is provided
                detailImage.setImage(new Image("resources/images/unnamed.jpg")); // Path to your default image
            }
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
    }

    public Button getReturnSearchBook() {
        return returnSearchBook;
    }
}
