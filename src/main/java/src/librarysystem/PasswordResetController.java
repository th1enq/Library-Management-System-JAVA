package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class PasswordResetController extends BaseController {

  public Button resetPassword;
  public Button backtoLogin;
  public PasswordField newPassword;
  public TextField newPasswordVisible;
  public Button newPasswordSeeing;
  public FontAwesomeIcon newPasswordSeeIcon;
  public PasswordField confirmPassword;
  public TextField confirmPasswordVisible;
  public Button confirmPasswordSeeing;
  public FontAwesomeIcon confirmPasswordSeeIcon;

  private String userName;

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void initialize() {
    newPasswordVisible.textProperty().bindBidirectional(newPassword.textProperty());
    confirmPasswordVisible.textProperty().bindBidirectional(confirmPassword.textProperty());

    backtoLogin.setOnAction(event -> {
      returnSignIn();
    });

    newPassword.requestFocus();
    newPassword.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        confirmPassword.requestFocus();
        event.consume();
      }
    });

    confirmPassword.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.TAB) {
        newPassword.requestFocus();
        event.consume();
      }
    });

    newPasswordSeeing.setOnAction(event -> {
      if (newPassword.isVisible()) {
        newPasswordSeeIcon.setGlyphName("EYE_SLASH");
        newPassword.setVisible(false);
        newPasswordVisible.setVisible(true);
      } else {
        newPasswordSeeIcon.setGlyphName("EYE");
        newPassword.setVisible(true);
        newPasswordVisible.setVisible(false);
      }
    });

    confirmPasswordSeeing.setOnAction(event -> {
      if (confirmPassword.isVisible()) {
        confirmPasswordSeeIcon.setGlyphName("EYE_SLASH");
        confirmPassword.setVisible(false);
        confirmPasswordVisible.setVisible(true);
      } else {
        confirmPasswordSeeIcon.setGlyphName("EYE");
        confirmPassword.setVisible(true);
        confirmPasswordVisible.setVisible(false);
      }
    });

    resetPassword.setOnAction(event -> {
      String newPasswordText = newPassword.getText();
      String confirmPasswordText = confirmPassword.getText();
      if (newPasswordText == null || newPasswordText.isEmpty()) {
        System.out.println("Vui long nhap mat khau moi !!!");
      } else if (confirmPasswordText == null || confirmPasswordText.isEmpty()) {
        System.out.println("Vui long nhap lai mat khau !!!");
      } else if (newPasswordText.equals(confirmPasswordText)) {
        if (DBInfo.isUsernameExists(userName)) {
          DBInfo.getUser(userName).update(null, null, newPasswordText, null);
          returnSignIn();
        }
      }
    });
  }
}
