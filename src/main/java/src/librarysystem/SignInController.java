package src.librarysystem;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.tools.javac.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import javax.swing.*;

public class SignInController extends BaseController implements Initializable {

    public javafx.scene.control.TextField userEmail;

    @FXML
    public Button passwordSeeing;
    @FXML
    public javafx.scene.control.TextField passwordVisible;
    @FXML
    public FontAwesomeIcon passwordSeeIcon;
    @FXML
    public Label signInErrorText;

    @FXML
    public HBox errorAlert;

    @FXML
    private PasswordField userPassword;

    @FXML
    private Button forgetPasswordButton;

    private boolean isPasswordVisible = false;

    private User currentUser = new User();

    private void returnHome() {
        MainGUI.setCurrentUser(currentUser);
        try {
            // Load MainGUI.fxml
            Parent mainRoot = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
            Scene mainScene = new Scene(mainRoot);

            // Get the current stage from any control in this scene
            Stage currentStage = (Stage) userEmail.getScene().getWindow();


            // Set the new scene on the current stage
            currentStage.setScene(mainScene);

            // Alternatively, you can set the position manually if you want more control
            double x = (Screen.getPrimary().getVisualBounds().getWidth() - currentStage.getWidth()) / 2;
            double y = (Screen.getPrimary().getVisualBounds().getHeight() - currentStage.getHeight()) / 2;
            currentStage.setX(x);
            currentStage.setY(y);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String content) {
        errorAlert.setVisible(true);
        signInErrorText.setText(content);
    }

    public void login() {
        if (userEmail.getText().isEmpty()) {
            showAlert("Bạn chưa nhập email !!!");
            return;
        }
        // Authenticate the user
        if (DBInfo.checkPass(userEmail.getText(), userPassword.getText())) {
            currentUser = DBInfo.getUser(userEmail.getText());
            String today = LocalDate.now().toString();
            String dayOfWeek = LocalDate.now().getDayOfWeek().toString();
            System.out.println(today + " " + dayOfWeek);
            ChartController.updateLoginCount(dayOfWeek);
            returnHome();
        } else {
            showAlert("Tài khoản hoặc mật khẩu không chính xác !!!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userEmail.requestFocus();

        passwordVisible.setManaged(false);
        passwordVisible.setVisible(false);

        // Bind the text of both fields
        passwordVisible.textProperty().bindBidirectional(userPassword.textProperty());

        errorAlert.setVisible(false);

        userEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                userPassword.requestFocus();
                event.consume(); // Prevent default tab behavior
            }
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        userPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                userEmail.requestFocus();
                event.consume(); // Prevent default tab behavior
            }
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        forgetPasswordButton.setOnAction(event -> {
            returnForgotPassword();
        });
    }

    public void togglePasswordVisibility(ActionEvent actionEvent) {
        if (isPasswordVisible) {
            passwordSeeIcon.setGlyphName("EYE_SLASH");
            // Show PasswordField, hide TextField
            passwordVisible.setVisible(false);
            passwordVisible.setManaged(false);
            userPassword.setVisible(true);
            userPassword.setManaged(true);
            isPasswordVisible = false;
        } else {
            // Show TextField, hide PasswordField
            passwordSeeIcon.setGlyphName("EYE");
            passwordVisible.setVisible(true);
            passwordVisible.setManaged(true);
            userPassword.setVisible(false);
            userPassword.setManaged(false);
            isPasswordVisible = true;
        }
    }
}