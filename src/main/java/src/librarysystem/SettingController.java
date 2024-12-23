package src.librarysystem;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class SettingController extends BaseController implements Initializable {

  @FXML
  public ImageView coverImage;
  public ImageView avatar;
  public Line lineMode;
  public ChoiceBox universityChoose;
  public ToggleButton darkModeToogleButton;
  public Pane paneContainer;
  public Label textTimeFormat;
  public Label textEmailNontifications;
  public Label textDarkMode;
  public Label textLanguage;
  public Label languageLabel;
  public Label timeLabel;
  public Label emailLabel;
  public Label labelDarkMode;
  public Label nameLabel;
  public TextField nameTextField;
  public Label userEmailLabel;
  public TextField emailTextField;
  public Label idLabel;
  public TextField idTextField;
  public Label universityLabel;
  public Label userLabel;
  public TextField userTextField;
  public Label phoneLabel;
  public TextField phoneTextField;
  public Button accountSettingLabel;
  public Button uiSettingLabel;
  public Button changePasswordLabel;
  public PasswordField newPassword;
  public PasswordField confirmNewPassword;
  public PasswordField oldPassword;
  public Label confirmNewPasswordLabel;
  public Label newPasswordLabel;
  public Label oldPasswordLabel;
  public Button confirmPasswordSee;
  public TextField confirmNewPasswordVisiable;
  public Button newPasswordSee;
  public TextField newPasswordVisiable;
  public Button oldPasswordSee;
  public TextField oldPasswordVisiable;
  public FontAwesomeIcon confirmNewPasswordIcon;
  public FontAwesomeIcon newPasswordIcon;
  public FontAwesomeIcon oldPasswordIcon;
  public Label bioName;
  public Label numBorrowedBooks;
  public Label numOverdueBooks;

  private int currentMode = 0;

  private ArrayList<Parent> uiSettingMode = new ArrayList<>();
  private ArrayList<Parent> profileSettingMode = new ArrayList<>();
  private ArrayList<Parent> changePasswordMode = new ArrayList<>();


  private ToggleSwitch toogleTimeFormat = new ToggleSwitch();
  private ToggleSwitch toogleEmailNontifications = new ToggleSwitch();

  /**
   * update.
   */
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
    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.25),
        lineMode);

    if (currentMode == 0) {
      translateTransition.setToX(0);
      // Set text color, transparent background, and hand cursor for mode 0
      accountSettingLabel.setStyle(
          "-fx-text-fill: #70727a; -fx-background-color: transparent; -fx-cursor: hand;");
      uiSettingLabel.setStyle(
          "-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
      changePasswordLabel.setStyle(
          "-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
    } else if (currentMode == 1) {
      translateTransition.setToX(200);
      // Set text color, transparent background, and hand cursor for mode 1
      accountSettingLabel.setStyle(
          "-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
      uiSettingLabel.setStyle(
          "-fx-text-fill: #70727a; -fx-background-color: transparent; -fx-cursor: hand;");
      changePasswordLabel.setStyle(
          "-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
    } else {
      translateTransition.setToX(400);
      // Set text color, transparent background, and hand cursor for mode 2
      accountSettingLabel.setStyle(
          "-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
      uiSettingLabel.setStyle(
          "-fx-text-fill: #bfc5cd; -fx-background-color: transparent; -fx-cursor: hand;");
      changePasswordLabel.setStyle(
          "-fx-text-fill: #70727a; -fx-background-color: transparent; -fx-cursor: hand;");
    }

    toogleTimeFormat.switchedOn.set(MainGUI.timeFormat24h);
    toogleEmailNontifications.switchedOn.set(MainGUI.showNotifications);

    // Play the transition
    translateTransition.play();
  }

  /**
   * mo account setting.
   */
  @FXML
  public void accountSetting(ActionEvent actionEvent) {
    if (currentMode == 0) {
      return;
    }
    currentMode = 0;
    update();
  }
  /**
   * mo Ui setting.
   */
  @FXML
  public void uiSetting(ActionEvent actionEvent) {
    if (currentMode == 1) {
      return;
    }
    currentMode = 1;
    update();
  }
  /**
   * doi mat khau.
   */
  @FXML
  public void changePassword(ActionEvent actionEvent) {
    if (currentMode == 2) {
      return;
    }
    currentMode = 2;
    update();
  }

  private boolean isOldPasswordVisible = false;
  private boolean isNewPasswordVisible = false;
  private boolean isConfirmPasswordVisible = false;


  /**
   * hien mat khau cu.
   */
  @FXML
  public void seeOldPasswod(ActionEvent actionEvent) {
    oldPasswordIcon.setGlyphName(isOldPasswordVisible ? "EYE_SLASH" : "EYE");
    oldPasswordVisiable.setVisible(!isOldPasswordVisible);
    oldPasswordVisiable.setManaged(!isOldPasswordVisible);
    oldPassword.setVisible(isOldPasswordVisible);
    oldPassword.setManaged(isOldPasswordVisible);
    isOldPasswordVisible = !isOldPasswordVisible;
  }

  /**
   * hien mat khau.
   */
  @FXML
  public void seeNewPassword(ActionEvent actionEvent) {
    newPasswordIcon.setGlyphName(isNewPasswordVisible ? "EYE_SLASH" : "EYE");
    newPasswordVisiable.setVisible(!isNewPasswordVisible);
    newPasswordVisiable.setManaged(!isNewPasswordVisible);
    newPassword.setVisible(isNewPasswordVisible);
    newPassword.setManaged(isNewPasswordVisible);
    isNewPasswordVisible = !isNewPasswordVisible;
  }
  /**
   * hien mat khau.
   */
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

    private TranslateTransition translateAnimation = new TranslateTransition(
        Duration.seconds(0.25));

    private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));

    private ParallelTransition animation = new ParallelTransition(translateAnimation,
        fillAnimation);

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

  /**
   * khoi tao.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    System.out.println(MainGUI.currentUser.toString());
    bioName.setText(MainGUI.currentUser.getName());
    nameTextField.setText(MainGUI.currentUser.getName());
    emailTextField.setText(MainGUI.currentUser.getUsername());
    idTextField.setText(MainGUI.currentUser.getMSV());
    userTextField.setText(MainGUI.currentUser.getUserType());
    phoneTextField.setText(MainGUI.currentUser.getPhone());
    universityChoose.setValue(String.valueOf(MainGUI.currentUser.getUniversity()));
    numBorrowedBooks.setText(String.valueOf(MainGUI.currentUser.getUpcoming()));
    numOverdueBooks.setText(String.valueOf(MainGUI.currentUser.getOverdue()));

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

    try {
      // Lấy đường dẫn từ user (ví dụ từ currentUser.getAvatarLink())
      Image userImage = new Image(MainGUI.currentUser.getCoverPhotoLink());
      coverImage.setImage(userImage); // Nếu tải được ảnh
    } catch (Exception e) {
      // Nếu không tải được ảnh, sử dụng ảnh mặc định "user.jpg"
      // Đảm bảo ảnh "user.jpg" nằm trong thư mục resources/images
      Image defaultImage = new Image(getClass().getResource("/images/bg_img.jpg").toExternalForm());
      coverImage.setImage(defaultImage);
    }

    double radius = 75; // Bán kính hình tròn (150 / 2)
    Circle clipAvatar = new Circle(75, 75, radius); // Tâm (75, 75) với bán kính 75
    avatar.setClip(clipAvatar);

    try {
      // Lấy đường dẫn từ user (ví dụ từ currentUser.getAvatarLink())
      Image userImage = new Image(MainGUI.currentUser.getAvatarLink());
      avatar.setImage(userImage); // Nếu tải được ảnh
    } catch (Exception e) {
      // Nếu không tải được ảnh, sử dụng ảnh mặc định "user.jpg"
      // Đảm bảo ảnh "user.jpg" nằm trong thư mục resources/images
      Image defaultImage = new Image(getClass().getResource("/images/user.jpg").toExternalForm());
      avatar.setImage(defaultImage);
    }

    // Set the stroke width (độ dày đường)
    lineMode.setStrokeWidth(2);

    // Set the stroke color (màu sắc)
    lineMode.setStroke(Color.web("#5769DB"));

    // Set the start and end positions
    lineMode.setStartX(-70); // Vị trí bắt đầu
    lineMode.setEndX(60);   // Vị trí kết thúc

    universityChoose.getItems().addAll("UET", "UED", "UL", "ULIS");

    toogleTimeFormat.setTranslateX(300);
    toogleTimeFormat.setTranslateY(48);
    toogleTimeFormat.switchedOn.set(MainGUI.timeFormat24h);
    paneContainer.getChildren().add(toogleTimeFormat);

    toogleEmailNontifications.setTranslateX(300);
    toogleEmailNontifications.setTranslateY(132);
    toogleTimeFormat.switchedOn.addListener((observable, oldValue, newValue) -> {
      textTimeFormat.setText(newValue ? "24-hour" : "12-hour");
      MainGUI.timeFormat24h = newValue; // Lưu trạng thái mới
    });
    toogleEmailNontifications.switchedOn.addListener((observable, oldValue, newValue) -> {
      textEmailNontifications.setText(newValue ? "Enabled" : "Disabled");
      MainGUI.showNotifications = newValue; // Lưu trạng thái mới
    });

    toogleEmailNontifications.switchedOn.set(MainGUI.showNotifications);
    paneContainer.getChildren().add(toogleEmailNontifications);

    uiSettingMode.add(textTimeFormat);
    uiSettingMode.add(textEmailNontifications);

    uiSettingMode.add(timeLabel);
    uiSettingMode.add(emailLabel);
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
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp",
            "*.gif"));

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
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp",
            "*.gif"));

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
    if (currentMode == 0) {
      MainGUI.currentUser.update(nameTextField.getText(), emailTextField.getText(), null,
          avatar.getImage().getUrl(), idTextField.getText(),
          String.valueOf(universityChoose.getValue()), phoneTextField.getText(),
          coverImage.getImage().getUrl(), null);
      sendNotification(1000, MainGUI.currentUser.getId(), "Successfully update information!!!", 0);
    } else if (currentMode == 2) {
      if (!PasswordUtils.verifyPassword(oldPassword.getText(), MainGUI.currentUser.getPassword())) {
        System.out.println(
            "Mat khau cu khong chinh xac" + " " + MainGUI.currentUser.getPassword() + " "
                + PasswordUtils.hashPassword(oldPassword.getText()) + " " + oldPassword.getText());
      } else {
        if (newPassword.getText().isEmpty() || newPassword == null) {
          System.out.println("Hay nhap mat khau moi");
          return;
        }
        if (confirmNewPassword.getText().isEmpty() || confirmNewPassword == null) {
          System.out.println("Hay xac nhan lai mat khau");
          return;
        }
        if (!newPassword.getText().equals(confirmNewPassword.getText())) {
          System.out.println("Mat khau nhap khong khop");
          return;
        }
        sendNotification(1000, MainGUI.currentUser.getId(), "Password change successfully !!!", 0);
        MainGUI.currentUser.update(null, null, newPassword.getText(), null);
      }
    }
  }
}