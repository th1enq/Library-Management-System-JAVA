package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseController {

  private static final Logger log = LogManager.getLogger(BaseController.class);
  private LoginController loginController;
  private MainGUI mainGUIController;

  /**
   * set controller
   *
   * @param loginController login controller
   */
  public void setLoginController(LoginController loginController) {
    this.loginController = loginController;
  }

  /**
   * sign in
   */
  protected void returnSignIn() {
    loginController.openSignInNoEffect(new ActionEvent());
  }

  /**
   * forgot password
   */
  protected void returnForgotPassword() {
    loginController.openForgotPassword();
  }

  /**
   * email send
   *
   * @param email email
   */
  protected void returnEmailSendCode(String email) {
    loginController.openEmailSendCode(email);
  }

  /**
   * password reset
   *
   * @param email userEmail
   */
  protected void returnPasswordReset(String email) {
    loginController.openPasswordReset(email);
  }

  /**
   * get the main VBox
   *
   * @return main VBox
   */
  protected VBox getVbox() {
    return loginController.getMainVbox();
  }

  /**
   * set the main controller
   *
   * @param mainGUIController main controller
   */
  public void setMainGUIController(MainGUI mainGUIController) {
    this.mainGUIController = mainGUIController;
  }

  /**
   * send the notifications
   *
   * @param senderId   sender
   * @param receiverId receiver
   * @param content    content
   * @param type       accept / deny
   */
  protected void sendNotification(int senderId, int receiverId, String content, int type) {
    mainGUIController.sendNotification(senderId, receiverId, content, type);
  }

  /**
   * view detail book
   *
   * @param currentBook current book
   * @param apiMode     current apiMode
   */
  protected void returnDetailBook(Book currentBook, boolean apiMode) {
    mainGUIController.returnDetailBook(currentBook, apiMode);
  }

  /**
   * update the notifications
   */
  protected void updateNotifications() {
    mainGUIController.updateNotifications();
  }

  /**
   * add book dashboard
   */
  protected void returnAddBook() {
    mainGUIController.returnAddBook();
  }

  /**
   * view book dashboard
   */
  protected void returnBookView() {
    mainGUIController.bookView();
  }
}

