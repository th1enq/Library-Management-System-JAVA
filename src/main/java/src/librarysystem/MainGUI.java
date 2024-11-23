package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class MainGUI implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public FontAwesomeIcon homeIcon;
    @FXML
    public FontAwesomeIcon bookIcon;
    @FXML
    public FontAwesomeIcon nontificationsIcon;
    public FontAwesomeIcon userIcon;
    public FontAwesomeIcon settingIcon;
    @FXML
    public Button nontificationsButton;
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
    private boolean buttonShowing = true;


    public static User currentUser = new User();

    public static void setCurrentUser(User x) {
        currentUser = x;
    }

    private boolean language = false;

    public static boolean apiSearchMode = false;
    public static String previousQuery = "";
    public static void setPreviousStage(boolean apiSearchMode, String previousQuery) {
        MainGUI.apiSearchMode = apiSearchMode;
        MainGUI.previousQuery = previousQuery;
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logOutButton) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();


                Stage stage = new Stage();

                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
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

//            dashBoardController.getSeeAllBook().setOnAction(event -> {
//                bookView();
//            });
//
//            dashBoardController.getAddBookButton().setOnAction(event -> {
//                addBook();
//            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
        System.out.println(currentUser.toString());
    }

    private Label loadingLabel;
    private String query;
    private ArrayList<Book> result;

    private void turnOnLoading() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingIndicator.fxml"));
            fxml = loader.load();

            LoadingController loadingIndicator = loader.getController();
            loadingLabel = loadingIndicator.getLoadingLabel();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateProgressLabel(Task<ArrayList<Book>> task) {
        task.progressProperty().addListener((obs, oldValue, newValue) -> {
            int percent = (int) (newValue.doubleValue() * 100);
            loadingLabel.setText(percent + "%");
        });
    }

    public void returnDetailBook(Book currentBook, boolean apiMode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookViewDetail.fxml"));
            fxml = loader.load();

            BookViewDetailController bookViewDetailController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            bookViewDetailController.initialize(currentBook);
            bookViewDetailController.setApiMode(apiMode);
            bookViewDetailController.getReturnSearchBook().setOnAction(e -> {
                bookView();
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update() {
        language = UISetting.getInstance().getVietNameseMode();
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
            case 3 :
                nontificationsIcon.getStyleClass().add("icon-active-color");
                nontificationsButton.getStyleClass().add("menu-btn-active");
                break;
            case 4 :
                settingIcon.getStyleClass().add("icon-active-color");
                settingButton.getStyleClass().add("menu-btn-active");
                break;
        }
    }

    private void reset() {
        // Clear the styles for all buttons and icons before applying new styles
        homeIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        bookIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        userIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        nontificationsIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        settingIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        logOutIcon.getStyleClass().removeAll("icon-active-color", "icon-color");

        homeButton.getStyleClass().removeAll("menu-btn-active", "parent");
        bookViewButton.getStyleClass().removeAll("menu-btn-active", "parent");
        userButton.getStyleClass().removeAll("menu-btn-active", "parent");
        nontificationsButton.getStyleClass().removeAll("menu-btn-active", "parent");
        settingButton.getStyleClass().removeAll("menu-btn-active", "parent");
        logOutButton.getStyleClass().removeAll("menu-btn-active", "parent");

        homeIcon.getStyleClass().add("icon-color");
        bookIcon.getStyleClass().add("icon-color");
        userIcon.getStyleClass().add("icon-color");
        nontificationsIcon.getStyleClass().add("icon-color");
        settingIcon.getStyleClass().add("icon-color");
        logOutIcon.getStyleClass().add("icon-color");


        homeButton.getStyleClass().add("parent");
        bookViewButton.getStyleClass().add("parent");
        userButton.getStyleClass().add("parent");
        nontificationsButton.getStyleClass().add("parent");
        settingButton.getStyleClass().add("parent");
        logOutButton.getStyleClass().add("parent");
    }

    private void fadeAnimation() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), mainVbox);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
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

            fadeAnimation();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void bookView() {
        currentStage = 1;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookView.fxml"));

            fxml = loader.load();

            SearchBookController searchBookController = loader.getController();
            searchBookController.setMainGUI(this);

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

            fadeAnimation();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    public void returnSetting(ActionEvent actionEvent) {
        currentStage = 4;
        try {
            fxml = FXMLLoader.load(getClass().getResource("Setting.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);

            fadeAnimation();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    public void returnNontifications(ActionEvent actionEvent) {
        currentStage = 3;
        try {
            fxml = FXMLLoader.load(getClass().getResource("Nontifications.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);

            fadeAnimation();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }
}