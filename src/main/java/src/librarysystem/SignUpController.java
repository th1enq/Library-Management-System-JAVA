package src.librarysystem;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import javax.swing.*;

public class SignUpController implements Initializable {

    public javafx.scene.control.TextField userEmail;

    @FXML
    public Button passwordSeeing;
    @FXML
    public javafx.scene.control.TextField passwordVisible;
    @FXML
    public FontAwesomeIcon passwordSeeIcon;
    @FXML
    public Label signUpErrorText;

    @FXML
    public HBox errorAlert;
    @FXML
    public TextField userStudentID;
    @FXML
    public TextField userName;
    public Button signUpButotn;

    @FXML
    private PasswordField userPassword;

    private boolean isPasswordVisible = false;

    private void returnHome() {

    }

    private void showAlert(String content) {
        errorAlert.setVisible(true);
        signUpErrorText.setText(content);
    }

    public void login() {
            
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordVisible.setManaged(false);
        passwordVisible.setVisible(false);
        passwordVisible.textProperty().bindBidirectional(userPassword.textProperty());
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

    @FXML
    public void createNewAccount(ActionEvent actionEvent) {
        if(userName.getText().isEmpty()) {
            showAlert("Bạn chưa nhập họ và tên !!!");
        }
        else if(userStudentID.getText().isEmpty()) {
            showAlert("Bạn chưa nhập mã sinh viên !!!");
        }
        else if(userEmail.getText().isEmpty()) {
            showAlert("Bạn chưa nhập email !!!");
        }
        else if(userPassword.getText().isEmpty()) {
            showAlert("Bạn chưa nhập mật khẩu !!!");
        }
        else if(!DBInfo.checkUnique(userEmail.getText())) {
            showAlert("Tài khoản đã tồn tại !!!");
        }
        else {
            showAlert("Đăng kí thành công !!!");
            DBInfo.Register(0, userName.getText(), userEmail.getText(), userPassword.getText(), "user", userStudentID.getText());
        }
    }
}