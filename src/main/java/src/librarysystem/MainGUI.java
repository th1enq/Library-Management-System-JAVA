package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MainGUI implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public FontAwesomeIcon homeIcon;
    @FXML
    public FontAwesomeIcon bookIcon;
    public FontAwesomeIcon statisticsIcon;
    public FontAwesomeIcon userIcon;
    public FontAwesomeIcon settingIcon;
    public Button statisticsButton;
    public Button userButton;
    public Button settingButton;
    public FontAwesomeIcon logOutIcon;
    @FXML
    public TextField bookQuery;
    @FXML
    private Button logOutButton;

    @FXML
    private Button minimizeButton;

    @FXML Button bookViewButton;

    @FXML
    private void minimze() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    private void close() {
        System.exit(0);
    }

    private double x;
    private double y;
    private int currentStage = 0;

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logOutButton) {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneY();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {

                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

                logOutButton.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private VBox mainVbox;

    private Parent fxml;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
            fxml = loader.load();
            DashBoardController dashBoardController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            dashBoardController.getSeeAllBook().setOnAction(event -> {
                bookView();
            });

            dashBoardController.getAddBookButton().setOnAction(event -> {
                addBook();
            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
        searchQuery();
    }

    private String query;
    private ArrayList<Book> result;

    private void turnOnLoading() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingIndicator.fxml"));
            fxml = loader.load();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchQuery() {
        bookQuery.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                currentStage = 1;
                query = bookQuery.getText();
                result = BookServices.searchBooks(query);

                turnOnLoading();

                bookQuery.clear();

                new Thread(() -> {
                    result = BookServices.searchBooks(query);

                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchBook.fxml"));
                            fxml = loader.load();

                            SearchBookController searchBookController = loader.getController();
                            searchBookController.populateTable(result);

                            mainVbox.getChildren().clear();
                            mainVbox.getChildren().setAll(fxml);

                            searchBookController.getSeeDetailBook().setOnAction(e -> {
                                returnDetailBook(searchBookController.getCurrentBook());
                            });

                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }).start();
            }
        });
        update();
    }

    private void returnSearchBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchBook.fxml"));
            fxml = loader.load();

            SearchBookController searchBookController = loader.getController();
            searchBookController.populateTable(result);

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            searchBookController.getSeeDetailBook().setOnAction(e -> {
                returnDetailBook(searchBookController.getCurrentBook());
            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void returnDetailBook(Book currentBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookViewDetail.fxml"));
            fxml = loader.load();

            BookViewDetailController bookViewDetailController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            bookViewDetailController.initialize(currentBook);
            bookViewDetailController.getReturnSearchBook().setOnAction(e -> {
                returnSearchBook();
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update() {
        reset();
        System.out.println(currentStage);
        switch (currentStage) {
            case 0:
                homeIcon.getStyleClass().add("icon-active-color");
                homeButton.getStyleClass().add("menu-btn-active");
                break;
            case 1:
                bookIcon.getStyleClass().add("icon-active-color");
                bookViewButton.getStyleClass().add("menu-btn-active");
                break;
            case 2 :
                userIcon.getStyleClass().add("icon-active-color");
                userButton.getStyleClass().add("menu-btn-active");
                break;
        }
    }

    private void reset() {
        // Clear the styles for all buttons and icons before applying new styles
        homeIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        bookIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        userIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        statisticsIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        settingIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        logOutIcon.getStyleClass().removeAll("icon-active-color", "icon-color");

        homeButton.getStyleClass().removeAll("menu-btn-active", "parent");
        bookViewButton.getStyleClass().removeAll("menu-btn-active", "parent");
        userButton.getStyleClass().removeAll("menu-btn-active", "parent");
        statisticsButton.getStyleClass().removeAll("menu-btn-active", "parent");
        settingButton.getStyleClass().removeAll("menu-btn-active", "parent");
        logOutButton.getStyleClass().removeAll("menu-btn-active", "parent");

        homeIcon.getStyleClass().add("icon-color");
        bookIcon.getStyleClass().add("icon-color");
        userIcon.getStyleClass().add("icon-color");
        statisticsIcon.getStyleClass().add("icon-color");
        settingIcon.getStyleClass().add("icon-color");
        logOutIcon.getStyleClass().add("icon-color");


        homeButton.getStyleClass().add("parent");
        bookViewButton.getStyleClass().add("parent");
        userButton.getStyleClass().add("parent");
        statisticsButton.getStyleClass().add("parent");
        settingButton.getStyleClass().add("parent");
        logOutButton.getStyleClass().add("parent");
    }

    @FXML
    private void returnHome() {
        currentStage = 0;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
            fxml = loader.load();
            DashBoardController dashBoardController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            dashBoardController.getSeeAllBook().setOnAction(event -> {
                bookView();
            });

            dashBoardController.getAddBookButton().setOnAction(event -> {
                addBook();
            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void bookView() {
        currentStage = 1;
        try {
            fxml = FXMLLoader.load(getClass().getResource("BookView.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void addBook() {
        currentStage = 1;
        try {
            fxml = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void userView() {
        currentStage = 2;
        try {
            fxml = FXMLLoader.load(getClass().getResource("userView.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }
}