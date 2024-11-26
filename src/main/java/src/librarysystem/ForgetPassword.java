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

public class ForgetPassword {
    @FXML
    public javafx.scene.control.TextField userEmail;  // TextField nhập email

    @FXML
    public Button sendEmail;

    public void SendEmail() {
        String email = userEmail.getText();
        EmailSender emailSend = new EmailSender();
        emailSend.sendEmail(email,"Mật khẩu khôi phục","Mật khẩu khôi phục của bạn là 12345");
        System.out.println("Gui thanh cong");
    }

    public void initialize() {
        sendEmail.setOnAction(actionEvent -> {
            SendEmail();
        });

        userEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                SendEmail();
            }
        });
    }
}
