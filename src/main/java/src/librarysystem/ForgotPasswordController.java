package src.librarysystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ForgotPasswordController extends BaseController {
    @FXML
    public TextField emailUser;
    @FXML
    public Button backtoLogin;
    @FXML
    public Button resetPassword;

    @FXML
    public void initialize() {
        backtoLogin.setOnAction(event -> {
            returnSignIn();
        });

        resetPassword.setOnAction(event -> {
            boolean successSender = PasswordRecoveryService.sendToken(emailUser.getText());
            returnEmailSendCode(emailUser.getText());
        });
    }
}
