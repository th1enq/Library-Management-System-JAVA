package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SearchBookController {

    @FXML
    public ImageView imageBook;
    @FXML
    public Label titleBook;
    @FXML
    public Label authorBook;
    @FXML
    public Button seeDetailBook;  // Adjust based on your Book class
    @FXML
    public ScrollPane stackPane;

    private int currentIndex = 0;
    private ArrayList<Book> bookLists;
    private ArrayList<Pane> paneList = new ArrayList<>();  // List to store all panes

    public void initialize() {

    }

    public void show(ArrayList<Book> books) {
        VBox vbox = new VBox(20); // Create a VBox for the books with 10px spacing
        vbox.getStyleClass().add("sidebar");
        vbox.setStyle("-fx-background-radius: 15px;");
        paneList.clear();
        for (Book book : books) {
            Pane bookPane = createBookPane(book); // Create a Pane for each book
            vbox.getChildren().add(bookPane); // Add the Pane to the VBox
            paneList.add(bookPane);
        }
        stackPane.setContent(vbox); // Set the VBox as the content of the ScrollPane
        bookLists = books;
    }


    private void processSelected(Pane pane, Book book) {
        currentIndex = paneList.indexOf(pane);
        if (selectedPane != null) {
            // Reset the background color of the previously selected pane
            selectedPane.setStyle("-fx-background-color: #FFFFFF;");
            selectedPane.setStyle("-fx-cursor: hand;");
        }

        // Set the background color of the currently selected pane
        pane.setStyle("-fx-background-color: #D0E8FF;");  // Soft blue for selected

        selectedPane = pane;  // Update selectedPane to the new selection
        updateMainDisplay(book);  // Load selected book details

        scrollToPane(currentIndex);
    }

    private void scrollToPane(int index) {
        if (paneList.isEmpty()) return;

        // Get the total height of the VBox (container for all panes)
        double totalHeight = paneList.size() * (200 + 20); // Pane height + VBox spacing

        // Calculate the target scroll position based on the pane's position
        double targetY = index * (200 + 20);

        // Calculate scroll position within bounds by considering the height of the ScrollPane's viewport
        double viewportHeight = stackPane.getViewportBounds().getHeight();
        double vboxHeight = totalHeight + 20;  // Adding extra spacing

        // Adjust scroll value to center the selected pane, so it appears in the viewport if possible
        double scrollValue = (targetY - (viewportHeight / 2)) / (vboxHeight - viewportHeight);

        // Ensure scrollValue is within the bounds of 0 to 1
        stackPane.setVvalue(Math.max(0, Math.min(scrollValue, 1)));
    }


    private Pane selectedPane = null;  // Track the currently selected pane

    private Pane createBookPane(Book book) {
        Pane pane = new Pane();
        pane.setPrefSize(668, 200);
        pane.getStyleClass().add("slider");
        pane.setStyle("-fx-cursor: hand;");

        // Create ImageView for book thumbnail
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(138);
        imageView.setLayoutX(42);
        imageView.setLayoutY(25);

        // Set image or default image if not available
        if (book.getThumbnail() != null && !book.getThumbnail().isEmpty()) {
            Image bookImage = new Image(book.getThumbnail());
            imageView.setImage(bookImage);
        } else {
            String defaultImagePath = getClass().getResource("/images/unnamed.jpg").toExternalForm();
            Image defaultImage = new Image(defaultImagePath);
            imageView.setImage(defaultImage);
        }
        pane.getChildren().add(imageView);

        // Create Title label
        Label title = new Label(book.getTitle());
        title.setLayoutX(212);
        title.setLayoutY(72);
        title.setFont(new Font("System Bold", 15));
        pane.getChildren().add(title);

        // Create Author label
        Label author = new Label(book.getAuthors());
        author.setLayoutX(212);
        author.setLayoutY(111);
        pane.getChildren().add(author);

        // Create ISBN label
        Label isbn = new Label(book.getISBN());
        isbn.setLayoutX(212);
        isbn.setLayoutY(149);
        pane.getChildren().add(isbn);

        // Create Publisher label
        Label publisher = new Label(book.getPublisher());
        publisher.setLayoutX(458);
        publisher.setLayoutY(102);
        pane.getChildren().add(publisher);

        // Create Publish Date label
        Label publishDate = new Label(book.getPublishedDate());
        publishDate.setLayoutX(458);
        publishDate.setLayoutY(140);
        pane.getChildren().add(publishDate);

        int rating = book.getRating();  // Get rating from Book instance
        double layoutX = 212.0;
        double layoutY = 48.0;
        double starSpacing = 24.0;

        for (int i = 0; i < 5; i++) {
            FontAwesomeIcon starIcon = new FontAwesomeIcon();
            starIcon.setGlyphName("STAR_ALT");
            starIcon.setLayoutX(layoutX + i * starSpacing);
            starIcon.setLayoutY(layoutY);
            starIcon.setSize("1.5em");

            // Set color based on the rating
            if (i < rating) {
            } else {
            }

            // Add each star icon to the pane
            pane.getChildren().add(starIcon);
        }


        // Handle pane selection
        pane.setOnMouseClicked(event -> {
            processSelected(pane, book);
        });

        return pane;
    }


    private void updateMainDisplay(Book book) {
        // Update the main display with the selected book's details
        if (book.getThumbnail() != null && !book.getThumbnail().isEmpty()) {
            imageBook.setImage(new Image(book.getThumbnail()));
        } else {
            String defaultImagePath = getClass().getResource("/images/unnamed.jpg").toExternalForm();
            imageBook.setImage(new Image(defaultImagePath));
        }
        titleBook.setText(book.getTitle());
        authorBook.setText(book.getAuthors());
    }


    public void previousBook(ActionEvent actionEvent) {
        if(bookLists != null) {
            currentIndex--;
            if(currentIndex < 0) {
                currentIndex = bookLists.size() - 1;
            }
            processSelected(paneList.get(currentIndex), bookLists.get(currentIndex));
        }
    }

    public void nextBook(ActionEvent actionEvent) {
        if(bookLists != null) {
            currentIndex++;
            if(currentIndex >= bookLists.size()) {
                currentIndex = 0;
            }
            processSelected(paneList.get(currentIndex), bookLists.get(currentIndex));
        }
    }

    public Button getSeeDetailBook() {
        return seeDetailBook;
    }

    public Book getCurrentBook() {
        return bookLists.get(currentIndex);
    }
}
