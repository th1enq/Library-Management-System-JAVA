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
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import javax.swing.*;

public class SignInController implements Initializable {

    public javafx.scene.control.TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    public void login() {
//        connect.reg
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}