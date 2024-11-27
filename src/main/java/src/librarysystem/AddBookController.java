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


  /**
   * handle event
   * @param actionEvent event
   */
  @FXML
  public void handleUploadImage(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Image");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
    );
    Stage stage = (Stage) uploadButton.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);

    if (file != null) {
      Image image = new Image(file.toURI().toString());

      imageView.setImage(image);

      imageView.setFitHeight(313);
      imageView.setFitWidth(263);
      imageView.setPreserveRatio(true);
    }
  }

  /**
   * save book.
   * @param actionEvent event
   */
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
    Book newBook = new Book(titleName, "Unknown", authorName, pushlisherBook, "Unknown",
        descriptionN, imageView.getImage().getUrl(), paperbackBook, categoryBook, priceX,
        languageBook, "Unknown");
    DBInfo.addBook(newBook);
    sendNotification(1000, 999, "Add new book cuccessfully !!!", 0);
    returnBookView();
  }
}
