package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class SearchBookController {

    @FXML
    public ImageView imageBook;
    @FXML
    public Label titleBook;
    @FXML
    public Label authorBook;
    @FXML
    public Button seeDetailBook;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> categoryColumn; // Adjust based on your Book class
    @FXML
    private TableColumn<Book, String> typeColumn;    // Adjust based on your Book class

    private int currentIndex = 0;
    private ArrayList<Book> bookLists;

    public void initialize() {
        // Initialize the TableView columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category")); // Modify according to your Book class
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));         // Modify according to your Book class

        bookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentIndex = bookTable.getSelectionModel().getSelectedIndex();
            if (newSelection != null) {
                displayBookDetails(newSelection);  // Call method to update UI with book details
            }
        });
    }

    @FXML
    private void displayBookDetails(Book book) {
        bookTable.getSelectionModel().select(currentIndex); // Select the current book row
        titleBook.setText(book.getTitle());
        authorBook.setText(book.getAuthors());
        if (book.getThumbnail() != null && !book.getThumbnail().isEmpty()) {
            Image bookImage = new Image(book.getThumbnail());
            imageBook.setImage(bookImage);
        } else {
            String defaultImagePath = getClass().getResource("/images/unnamed.jpg").toExternalForm();
            Image defaultImage = new Image(defaultImagePath);
            imageBook.setImage(defaultImage);
        }
    }

    public void populateTable(ArrayList<Book> books) {
        bookLists = books;
        bookTable.getItems().clear(); // Clear existing items

        for (Book book : books) {
            bookTable.getItems().add(book); // Add new book entries to the TableView
        }

        if(!books.isEmpty()) {
            currentIndex = 0;
            displayBookDetails(books.get(currentIndex));
        }
    }

    public void previousBook(ActionEvent actionEvent) {
        if(bookLists != null) {
            currentIndex--;
            if(currentIndex < 0) {
                currentIndex = bookLists.size() - 1;
            }
            displayBookDetails(bookLists.get(currentIndex));
        }
    }

    public void nextBook(ActionEvent actionEvent) {
        if(bookLists != null) {
            currentIndex++;
            if(currentIndex >= bookLists.size()) {
                currentIndex = 0;
            }
            displayBookDetails(bookLists.get(currentIndex));
        }
    }

    public Button getSeeDetailBook() {
        return seeDetailBook;
    }

    public Book getCurrentBook() {
        return bookLists.get(currentIndex);
    }
}
