package src.librarysystem;

import java.net.URL;
import java.util.ResourceBundle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class SignUpController implements Initializable {

  @FXML
  public TextField userEmail;
  public Button passwordSeeing;
  public javafx.scene.control.TextField passwordVisible;
  public FontAwesomeIcon passwordSeeIcon;
  public Label signUpErrorText;
  public HBox errorAlert;
  public TextField userStudentID;
  public TextField userName;
  public Button signUpButotn;
  public PasswordField userPassword;

  private boolean isPasswordVisible = false;

  private void showAlert(String content) {
    errorAlert.setVisible(true);
    signUpErrorText.setText(content);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    passwordVisible.setManaged(false);
    passwordVisible.setVisible(false);
    passwordVisible.textProperty().bindBidirectional(userPassword.textProperty());

    userName.requestFocus();
    userName.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        userStudentID.requestFocus();
        event.consume();
      }
    });
    userStudentID.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        userEmail.requestFocus();
        event.consume();
      }
    });
    userEmail.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        userPassword.requestFocus();
        event.consume();
      }
    });
    userPassword.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        userName.requestFocus();
        event.consume();
      }
    });
  }

  public void togglePasswordVisibility(ActionEvent actionEvent) {
    if (isPasswordVisible) {
      passwordSeeIcon.setGlyphName("EYE_SLASH");
      passwordVisible.setVisible(false);
      passwordVisible.setManaged(false);
      userPassword.setVisible(true);
      userPassword.setManaged(true);
      isPasswordVisible = false;
    } else {
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
    if (userName.getText().isEmpty()) {
      showAlert("Please enter your name !!!");
    } else if (userStudentID.getText().isEmpty()) {
      showAlert("Please enter your student ID");
    } else if (userEmail.getText().isEmpty()) {
      showAlert("Please enter your email !!!");
    } else if (userPassword.getText().isEmpty()) {
      showAlert("Please enter your password !!!");
    } else if (!DBInfo.checkUnique(userEmail.getText())) {
      showAlert("Your account already exists !!!");
    } else if (!DBInfo.validUsername(userEmail.getText())) {
      showAlert("Invalid email. Try again !!!");
    } else if (!DBInfo.validPassword(userPassword.getText())) {
      showAlert("Your password is too weak. Try again !!!");
    } else {
      showAlert("You have successfully registered an account !!!");
      DBInfo.Register(0, userName.getText(), userEmail.getText(), userPassword.getText(), "user",
          userStudentID.getText());
    }
  }
}