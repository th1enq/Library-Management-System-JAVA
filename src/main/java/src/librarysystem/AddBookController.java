package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddBookController {

    @FXML
    public ImageView imageView;
    @FXML
    public Button uploadButton;

    // Nếu ấn vào nút chọn ảnh
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

    }
}
