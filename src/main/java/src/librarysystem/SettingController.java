package src.librarysystem;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.tools.javac.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class SettingController implements Initializable {

    @FXML
    public ImageView coverImage;
    @FXML
    public ImageView avatar;
    @FXML
    public Line lineMode;
    @FXML
    public ChoiceBox universityChoose;
    @FXML
    public ToggleButton darkModeToogleButton;
    @FXML
    public Pane paneContainer;
    @FXML
    public Label textTimeFormat;
    @FXML
    public Label textEmailNontifications;
    @FXML
    public Label textDarkMode;
    @FXML
    public Label textLanguage;
    @FXML
    public Label languageLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label labelDarkMode;
    @FXML
    public Label nameLabel;
    @FXML
    public TextField nameTextField;
    @FXML
    public Label userEmailLabel;
    @FXML
    public TextField emailTextField;
    @FXML
    public Label idLabel;
    @FXML
    public TextField idTextField;
    @FXML
    public Label universityLabel;
    @FXML
    public Label userLabel;
    @FXML
    public TextField userTextField;
    @FXML
    public Label phoneLabel;
    @FXML
    public TextField phoneTextField;
    @FXML
    public Button accountSettingLabel;
    @FXML
    public Button uiSettingLabel;
    @FXML
    public Button changePasswordLabel;
    @FXML
    public PasswordField newPassword;
    @FXML
    public PasswordField confirmNewPassword;
    @FXML
    public PasswordField oldPassword;
    @FXML
    public Label confirmNewPasswordLabel;
    @FXML
    public Label newPasswordLabel;
    @FXML
    public Label oldPasswordLabel;
    @FXML
    public Button confirmPasswordSee;
    @FXML
    public TextField confirmNewPasswordVisiable;
    @FXML
    public Button newPasswordSee;
    @FXML
    public TextField newPasswordVisiable;
    @FXML
    public Button oldPasswordSee;
    @FXML
    public TextField oldPasswordVisiable;
    @FXML
    public FontAwesomeIcon confirmNewPasswordIcon;
    @FXML
    public FontAwesomeIcon newPasswordIcon;
    @FXML
    public FontAwesomeIcon oldPasswordIcon;
    @FXML
    public Label bioName;

    private int currentMode = 0;

    private ArrayList<Parent> uiSettingMode = new ArrayList<>();
    private ArrayList<Parent> profileSettingMode = new ArrayList<>();
    private ArrayList<Parent> changePasswordMode = new ArrayList<>();

    private void update() {
        // Update visibility of UI and profile settings based on currentMode
        for (Parent element : profileSettingMode) {
            element.setVisible((currentMode == 0) ? true : false);
        }

        for (Parent element : uiSettingMode) {
            element.setVisible((currentMode == 1) ? true : false);
        }

        for (Parent elememt : changePasswordMode) {
            elememt.setVisible((currentMode == 2) ? true : false);
        }
        oldPasswordVisiable.setVisible(false);
        confirmNewPasswordVisiable.setVisible(false);
        newPasswordVisiable.setVisible(false);

        // Set position for lineMode based on currentMode
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.25), lineMode);

        if (currentMode == 0) {
            translateTransition.setToX(0);
            // Set text color, transparent background, and hand cursor for mode 0
            accountSettingLabel.setStyle("-fx-text-fill: #70727a; -fx-background-color: transparent; -fx-cursor: hand;");
            uiSettingLabel.setStyle("-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
            changePasswordLabel.setStyle("-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
        } else if (currentMode == 1) {
            translateTransition.setToX(200);
            // Set text color, transparent background, and hand cursor for mode 1
            accountSettingLabel.setStyle("-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
            uiSettingLabel.setStyle("-fx-text-fill: #70727a; -fx-background-color: transparent; -fx-cursor: hand;");
            changePasswordLabel.setStyle("-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
        } else {
            translateTransition.setToX(400);
            // Set text color, transparent background, and hand cursor for mode 2
            accountSettingLabel.setStyle("-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
            uiSettingLabel.setStyle("-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
            changePasswordLabel.setStyle("-fx-text-fill: #70727a; -fx-background-color: transparent; -fx-cursor: hand;");
        }

        // Play the transition
        translateTransition.play();
    }



    @FXML
    public void accountSetting(ActionEvent actionEvent) {
        if(currentMode == 0) return;
        currentMode = 0;
        update();
    }

    @FXML
    public void uiSetting(ActionEvent actionEvent) {
        if(currentMode == 1) return;
        currentMode = 1;
        update();
    }

    @FXML
    public void changePassword(ActionEvent actionEvent) {
        if(currentMode == 2) return;
        currentMode = 2;
        update();
    }

    private boolean isOldPasswordVisible= false;
    private boolean isNewPasswordVisible= false;
    private boolean isConfirmPasswordVisible= false;


    @FXML
    public void seeOldPasswod(ActionEvent actionEvent) {
        oldPasswordIcon.setGlyphName(isOldPasswordVisible ? "EYE_SLASH" : "EYE");
        oldPasswordVisiable.setVisible(!isOldPasswordVisible);
        oldPasswordVisiable.setManaged(!isOldPasswordVisible);
        oldPassword.setVisible(isOldPasswordVisible);
        oldPassword.setManaged(isOldPasswordVisible);
        isOldPasswordVisible = !isOldPasswordVisible;
    }

    @FXML
    public void seeNewPassword(ActionEvent actionEvent) {
        newPasswordIcon.setGlyphName(isNewPasswordVisible ? "EYE_SLASH" : "EYE");
        newPasswordVisiable.setVisible(!isNewPasswordVisible);
        newPasswordVisiable.setManaged(!isNewPasswordVisible);
        newPassword.setVisible(isNewPasswordVisible);
        newPassword.setManaged(isNewPasswordVisible);
        isNewPasswordVisible = !isNewPasswordVisible;
    }

    @FXML
    public void seeConfirnPassword(ActionEvent actionEvent) {
        confirmNewPasswordIcon.setGlyphName(isConfirmPasswordVisible ? "EYE_SLASH" : "EYE");
        confirmNewPasswordVisiable.setVisible(!isConfirmPasswordVisible);
        confirmNewPasswordVisiable.setManaged(!isConfirmPasswordVisible);
        confirmNewPassword.setVisible(isConfirmPasswordVisible);
        confirmNewPassword.setManaged(isConfirmPasswordVisible);
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
    }

    private static class ToggleSwitch extends Parent {
        private BooleanProperty switchedOn = new SimpleBooleanProperty(false);

        private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.25));

        private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));

        private ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation);

        public BooleanProperty switchedOnProperty() {
            return switchedOn;
        }

        public ToggleSwitch() {

            Rectangle background = new Rectangle(50, 25);
            background.setArcWidth(25);
            background.setArcHeight(25);
            background.setFill(Color.WHITE);
            background.setStroke(Color.LIGHTGRAY);

            Circle trigger = new Circle((double) 25 / 2);
            trigger.setCenterX((double) 25 / 2);
            trigger.setCenterY((double) 25 / 2);
            trigger.setFill(Color.WHITE);

            trigger.setStroke(Color.LIGHTGRAY);

            translateAnimation.setNode(trigger);
            fillAnimation.setShape(background);

            switchedOn.addListener((obs, oldState, newState) -> {
                boolean isOn = newState.booleanValue();
                translateAnimation.setToX(isOn ? 50 - 25 : 0);
                fillAnimation.setFromValue(isOn ? Color.WHITE : Color.LIGHTGREEN);
                fillAnimation.setToValue(isOn ? Color.LIGHTGREEN : Color.WHITE);

                animation.play();
            });

            setOnMouseClicked(event -> {
                switchedOn.set(!switchedOn.get());
            });

            getChildren().addAll(background, trigger);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bioName.setText(MainGUI.currentUser.getName());
        nameTextField.setText(MainGUI.currentUser.getName());
        emailTextField.setText(MainGUI.currentUser.getUsername());
        idTextField.setText(String.valueOf(MainGUI.currentUser.getId()));
        userTextField.setText(MainGUI.currentUser.getUserType());


        oldPasswordVisiable.textProperty().bindBidirectional(oldPassword.textProperty());
        newPasswordVisiable.textProperty().bindBidirectional(newPassword.textProperty());
        confirmNewPasswordVisiable.textProperty().bindBidirectional(confirmNewPassword.textProperty());


        Path clip;
        double height = coverImage.getFitHeight();
        double width = coverImage.getFitWidth();
        double radius1 = 20;
        double radius2 = height * 0;
        clip = new Path(new MoveTo(0, radius1), new ArcTo(radius1, radius1, 0, radius1, 0, false, true),
                new HLineTo(width),
                new VLineTo(height - radius2),
                new ArcTo(radius2, radius2, 0, width - radius2, height, false, true),
                new HLineTo(0));

        clip.setFill(Color.ALICEBLUE);
        coverImage.setClip(clip);

        double radius = 75; // Bán kính hình tròn (150 / 2)
        Circle clipAvatar = new Circle(75, 75, radius); // Tâm (75, 75) với bán kính 75
        avatar.setClip(clipAvatar);

        // Set the stroke width (độ dày đường)
        lineMode.setStrokeWidth(2);

        // Set the stroke color (màu sắc)
        lineMode.setStroke(Color.web("#5769DB"));

        // Set the start and end positions
        lineMode.setStartX(-70); // Vị trí bắt đầu
        lineMode.setEndX(60);   // Vị trí kết thúc

        universityChoose.getItems().addAll("UET", "UED", "UL", "ULIS");

        ToggleSwitch toogleDarkMode = new ToggleSwitch();
        toogleDarkMode.setTranslateX(300);
        toogleDarkMode.setTranslateY(48);
        toogleDarkMode.switchedOn.addListener((observable, oldValue, newValue) -> {
            textDarkMode.setText(newValue ? "On" : "Off");
        });
        paneContainer.getChildren().add(toogleDarkMode);

        ToggleSwitch toogleTimeFormat = new ToggleSwitch();
        toogleTimeFormat.setTranslateX(300);
        toogleTimeFormat.setTranslateY(132);
        toogleTimeFormat.switchedOn.addListener((observable, oldValue, newValue) -> {
            textTimeFormat.setText(newValue ? "12-hour" : "24-hour");
        });
        paneContainer.getChildren().add(toogleTimeFormat);

        ToggleSwitch toogleLanguage = new ToggleSwitch();
        toogleLanguage.setTranslateX(300);
        toogleLanguage.setTranslateY(216);
        toogleLanguage.switchedOn.addListener((observable, oldValue, newValue) -> {
            textLanguage.setText(newValue ? "Vietnamese" : "English");
        });
        paneContainer.getChildren().add(toogleLanguage);

        ToggleSwitch toogleEmailNontifications = new ToggleSwitch();
        toogleEmailNontifications.setTranslateX(300);
        toogleEmailNontifications.setTranslateY(300);
        toogleEmailNontifications.switchedOn.addListener((observable, oldValue, newValue) -> {
            textEmailNontifications.setText(newValue ? "Enabled" : "Disabled");
        });
        paneContainer.getChildren().add(toogleEmailNontifications);


        uiSettingMode.add(textTimeFormat);
        uiSettingMode.add(textEmailNontifications);
        uiSettingMode.add(textDarkMode);
        uiSettingMode.add(textLanguage);
        uiSettingMode.add(languageLabel);
        uiSettingMode.add(timeLabel);
        uiSettingMode.add(emailLabel);
        uiSettingMode.add(labelDarkMode);
        uiSettingMode.add(toogleDarkMode);
        uiSettingMode.add(toogleLanguage);
        uiSettingMode.add(toogleEmailNontifications);
        uiSettingMode.add(toogleTimeFormat);

        profileSettingMode.add(nameLabel);
        profileSettingMode.add(nameTextField);
        profileSettingMode.add(userEmailLabel);
        profileSettingMode.add(emailTextField);
        profileSettingMode.add(userTextField);
        profileSettingMode.add(userLabel);
        profileSettingMode.add(idLabel);
        profileSettingMode.add(idTextField);
        profileSettingMode.add(phoneLabel);
        profileSettingMode.add(phoneTextField);
        profileSettingMode.add(universityChoose);
        profileSettingMode.add(universityLabel);

        changePasswordMode.add(oldPassword);
        changePasswordMode.add(oldPasswordLabel);
        changePasswordMode.add(newPassword);
        changePasswordMode.add(newPasswordLabel);
        changePasswordMode.add(confirmNewPassword);
        changePasswordMode.add(confirmNewPasswordLabel);
        changePasswordMode.add(oldPasswordSee);
        changePasswordMode.add(oldPasswordVisiable);
        changePasswordMode.add(newPasswordSee);
        changePasswordMode.add(newPasswordVisiable);
        changePasswordMode.add(confirmPasswordSee);
        changePasswordMode.add(confirmNewPasswordVisiable);

        update();
    }

    @FXML
    public void editAvatar(ActionEvent actionEvent) {
        // Tạo đối tượng FileChooser để chọn tệp ảnh
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));

        // Mở hộp thoại để người dùng chọn tệp ảnh
        File selectedFile = fileChooser.showOpenDialog(null);

        // Kiểm tra nếu người dùng chọn tệp
        if (selectedFile != null) {
            // Tạo đối tượng Image từ tệp ảnh đã chọn
            Image image = new Image(selectedFile.toURI().toString());

            // Đặt ảnh vào ImageView avatar
            avatar.setImage(image);
        }
    }


    @FXML
    public void editCoverImage(ActionEvent actionEvent) {
        // Tạo đối tượng FileChooser để chọn tệp ảnh
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));

        // Mở hộp thoại để người dùng chọn tệp ảnh
        File selectedFile = fileChooser.showOpenDialog(null);

        // Kiểm tra nếu người dùng chọn tệp
        if (selectedFile != null) {
            // Tạo đối tượng Image từ tệp ảnh đã chọn
            Image image = new Image(selectedFile.toURI().toString());

            // Đặt ảnh vào ImageView coverImage
            coverImage.setImage(image);
        }
    }


    @FXML
    public void updateProfile(ActionEvent actionEvent) {
       if(currentMode == 0) {
            MainGUI.currentUser.update(nameTextField.getText(), emailTextField.getText(), null, null);
        }
        else if(currentMode == 2) {
            if(!MainGUI.currentUser.getPassword().equals(oldPassword.getText())) {
                System.out.println("Mat khau cu khong chinh xac" + " " + MainGUI.currentUser.getPassword() + " " + oldPassword.getText());
            }
            else {
                if(newPassword.getText().isEmpty() || newPassword == null) {
                    System.out.println("Hay nhap mat khau moi");
                    return;
                }
                if(confirmNewPassword.getText().isEmpty() || confirmNewPassword == null) {
                    System.out.println("Hay xac nhan lai mat khau");
                    return;
                }
                if(!newPassword.getText().equals(confirmNewPassword.getText())) {
                    System.out.println("Mat khau nhap khong khop");
                    return;
                }
                System.out.println("Doi mat khau thanh cong" + " " + newPassword.getText());
                MainGUI.currentUser.update(null, null, newPassword.getText(), null);
            }
        }
    }
}
