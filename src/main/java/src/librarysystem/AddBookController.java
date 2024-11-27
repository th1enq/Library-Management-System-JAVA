package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddBookController extends BaseController {

    @FXML
    public ImageView imageView;
    @FXML
    public Button uploadButton;
    public TextField pushlisher;
    public TextField language;
    public TextField paperback;
    public TextArea description;
    public TextField price;
    public TextField category;
    public TextField rating;
    public TextField title;
    public TextField author;

    @FXML
    public void handleUploadImage(ActionEvent actionEvent) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        // Add extension filters to limit file selection to image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the file open dialog
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        // Check if a file was selected
        if (file != null) {
            // Create an Image object from the file
            Image image = new Image(file.toURI().toString());

            // Set the image to the ImageView
            imageView.setImage(image);

            // Optional: Ensure the image fits within the ImageView's dimensions
            imageView.setFitHeight(313);
            imageView.setFitWidth(263);
            imageView.setPreserveRatio(true);  // Maintain the aspect ratio
        }
    }

    @FXML
    public void saveBook(ActionEvent actionEvent) {
        String titleName = title.getText();
        String authorName = author.getText();
        String descriptionN = description.getText();
        String priceX = price.getText();
        String categoryBook = category.getText();
        String pushlisherBook = pushlisher.getText();
        String languageBook = language.getText();
        String paperbackBook = paperback.getText();
        Book newBook = new Book(titleName, "Unknown", authorName, pushlisherBook, "Unknown", descriptionN, imageView.getImage().getUrl(), paperbackBook, categoryBook, priceX, languageBook, "Unknown");
        DBInfo.addBook(newBook);
        sendNotification(1000, 999, "Add new book cuccessfully !!!", 0);
        returnBookView();
    }
}
