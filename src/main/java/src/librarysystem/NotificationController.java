package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class NotificationController extends BaseController {

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Pane nonNotiPane;

    public void displayNotifications() {
        if (MainGUI.currentUser.getNotifications().isEmpty()) {
            nonNotiPane.setVisible(true);
            scrollPane.setContent(nonNotiPane);
        } else {
            nonNotiPane.setVisible(false);
            VBox vbox = new VBox();
            vbox.setSpacing(20);
            for (Notification notification : MainGUI.currentUser.getNotifications()) {
                Pane notificationPane = createNotificationPane(notification);
                vbox.getChildren().add(notificationPane);
            }
            scrollPane.setContent(vbox);
        }
    }

    @FXML
    public void initialize() {
        displayNotifications();
    }

    // Tạo một Pane cho mỗi thông báo
    private Pane createNotificationPane(Notification notification) {
        Pane pane = new Pane();
        pane.setStyle(
                "-fx-border-color: #666565; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        pane.setPrefHeight(157);
        pane.setPrefWidth(1134);

        // Thêm biểu tượng
        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setGlyphName("WARNING");
        icon.setLayoutX(28);
        icon.setLayoutY(47);
        icon.setSize("2em");

        // Thêm tiêu đề
        Label titleLabel = new Label("Notification from " + notification.getReceiver());
        titleLabel.setLayoutX(82);
        titleLabel.setLayoutY(23);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Thêm thông điệp
        Label messageLabel = new Label(notification.getMessage());
        messageLabel.setLayoutX(82);
        messageLabel.setLayoutY(70);
        messageLabel.setStyle("-fx-font-size: 15px;");

        // Thêm thời gian
        Label timestampLabel = new Label(notification.getCreatedAt().toString());
        timestampLabel.setLayoutX(82);
        timestampLabel.setLayoutY(106);
        timestampLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #666565;");

        // Thêm nút đóng
        Button closeButton = new Button();
        closeButton.setLayoutX(1095);
        closeButton.setLayoutY(9);
        closeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        FontAwesomeIcon closeIcon = new FontAwesomeIcon();
        closeIcon.setGlyphName("CLOSE");
        closeIcon.setSize("1.7em");
        closeButton.setGraphic(closeIcon);

        closeButton.setOnAction(event -> {
            MainGUI.currentUser.deleteOneNotification(notification);
            displayNotifications();
            updateNotifications();
        });

        // Thêm các phần tử vào Pane
        pane.getChildren().addAll(icon, titleLabel, messageLabel, timestampLabel, closeButton);

        return pane;
    }

    @FXML
    public void clearAllNoti(ActionEvent actionEvent) {
        MainGUI.currentUser.deleteNotifications();
        sendNotification(1000, 1000, "Deleted all notifications !!!",0);
        displayNotifications();
    }
}