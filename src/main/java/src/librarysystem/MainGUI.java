package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
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
            fxml = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
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
            fxml = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);
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
}
