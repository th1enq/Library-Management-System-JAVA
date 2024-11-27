package src.librarysystem;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import javax.swing.*;

public class LoginController implements Initializable {

  @FXML
  public AnchorPane loginParent;
  @FXML
  private VBox vbox;

  private Parent fxml;
  /**
   * khoi tao.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    openSignIn(new ActionEvent());
  }
  /**
   * khoi tao.
   */
  public VBox getMainVbox() {
    return vbox;
  }

  /**
   * mo man hinh dang nhap.
   */
  @FXML
  public void openSignIn(ActionEvent event) {
    TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
    t.setToX(vbox.getLayoutX() * 5);
    t.play();
    t.setOnFinished((e) -> {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
        fxml = loader.load();
        SignInController signInController = loader.getController();
        signInController.setLoginController(this);
        vbox.getChildren().removeAll();
        vbox.getChildren().setAll(fxml);
      } catch (IOException ex) {
        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
  }

  /**
   * mo man hinh dang nhap.
   */
  @FXML
  public void openSignInNoEffect(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
      fxml = loader.load();
      SignInController signInController = loader.getController();
      signInController.setLoginController(this);
      vbox.getChildren().removeAll();
      vbox.getChildren().setAll(fxml);
    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * mo man hinh lay lai mat khau.
   */
  @FXML
  public void openPasswordReset(String email) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("PasswordReset.fxml"));
      fxml = loader.load();
      PasswordResetController passwordResetController = loader.getController();
      passwordResetController.setLoginController(this);
      passwordResetController.setUserName(email);
      vbox.getChildren().removeAll();
      vbox.getChildren().setAll(fxml);
    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  /**
   * mo man hinh lay lai mat khau.
   */
  @FXML
  public void openForgotPassword() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
      fxml = loader.load();
      ForgotPasswordController forgotPasswordController = loader.getController();
      forgotPasswordController.setLoginController(this);
      vbox.getChildren().removeAll();
      vbox.getChildren().setAll(fxml);
    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  /**
   * mo man hinh gui email.
   */
  @FXML
  public void openEmailSendCode(String email) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EmailSendCode.fxml"));
      fxml = loader.load();
      EmailSendCodeController emailSendCodeController = loader.getController();
      emailSendCodeController.setLoginController(this);
      emailSendCodeController.setEmail(email);
      vbox.getChildren().removeAll();
      vbox.getChildren().setAll(fxml);
    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * mo man hinh dang ki.
   */
  @FXML
  private void openSignUp(ActionEvent event) {
    TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
    t.setToX(17);
    t.play();
    t.setOnFinished((e) -> {
      try {
        fxml = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        vbox.getChildren().removeAll();
        vbox.getChildren().setAll(fxml);
      } catch (IOException ex) {
        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
      }
    });
  }

  public AnchorPane getLoginParent() {
    return loginParent;
  }

  @FXML
  private TextField studentEmail;

  @FXML
  private PasswordField studentPassword;

  @FXML
  public void login() {
    System.out.println("Login!!!");
  }
}