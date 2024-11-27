package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseController {
    private static final Logger log = LogManager.getLogger(BaseController.class);
    private LoginController loginController;
    private MainGUI mainGUIController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    protected void returnSignIn() {
        loginController.openSignInNoEffect(new ActionEvent());
    }

    protected void returnForgotPassword() {
        loginController.openForgotPassword();
    }

    protected void returnEmailSendCode(String email) {
        loginController.openEmailSendCode(email);
    }

    protected void returnPasswordReset(String email) {
        loginController.openPasswordReset(email);
    }

    protected VBox getVbox() { return loginController.getMainVbox(); }

    public void setMainGUIController(MainGUI mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    protected void sendNotification(int senderId, int receiverId, String content,int type) {
        mainGUIController.sendNotification(senderId, receiverId, content,type);
    }

    protected void returnDetailBook(Book currentBook, boolean apiMode) {
        mainGUIController.returnDetailBook(currentBook, apiMode);
    }

    protected void updateNotifications() {
        mainGUIController.updateNotifications();
    }
}

