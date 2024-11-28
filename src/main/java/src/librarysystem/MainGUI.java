package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Region;

import javafx.event.ActionEvent;

public class MainGUI implements Initializable {

  @FXML
  public Button homeButton;
  @FXML
  public FontAwesomeIcon homeIcon;
  @FXML
  public FontAwesomeIcon bookIcon;
  @FXML
  public FontAwesomeIcon nontificationsIcon;
  public FontAwesomeIcon userIcon;
  public FontAwesomeIcon settingIcon;
  @FXML
  public Button nontificationsButton;
  public Button userButton;
  public Button settingButton;
  public FontAwesomeIcon logOutIcon;
  @FXML
  public TextField bookQuery;
  @FXML
  public Pane popUpPane;
  @FXML
  public Label labelNoti;
  @FXML
  public Label contentNoti;
  @FXML
  public ImageView imageNoti;
  @FXML
  public Line timeNotiStroke;
  @FXML
  public Label numberNontifications;
  @FXML
  public ImageView profileImage;
  @FXML
  public Button bookIssueButton;
  @FXML
  public FontAwesomeIcon bookIssueIcon;
  @FXML
  private Button logOutButton;

  @FXML
  private Button minimizeButton;

  @FXML
  Button bookViewButton;

  static boolean timeFormat24h = false;
  static boolean showNotifications = true;

  @FXML
  private void minimze() {
    Stage stage = (Stage) minimizeButton.getScene().getWindow();
    stage.setIconified(true);
  }


  @FXML
  private void close() {
    System.exit(0);
  }

  private double x;
  private double y;
  private int currentStage = 0;
  private boolean buttonShowing = true;


  public static User currentUser = new User();

  public static void setCurrentUser(User x) {
    currentUser = x;
  }

  private boolean language = false;

  public static boolean apiSearchMode = false;
  public static String previousQuery = "";

  public static void setPreviousStage(boolean apiSearchMode, String previousQuery) {
    MainGUI.apiSearchMode = apiSearchMode;
    MainGUI.previousQuery = previousQuery;
  }

  /**
   * thoat.
   */
  @FXML
  public void logout(ActionEvent event) {
    try {
      if (event.getSource() == logOutButton) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        logOutButton.getScene().getWindow().hide();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private VBox mainVbox;

  private Parent fxml;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
      fxml = loader.load();
      DashBoardController dashBoardController = loader.getController();

      mainVbox.getChildren().clear();
      mainVbox.getChildren().setAll(fxml);

//            dashBoardController.getSeeAllBook().setOnAction(event -> {
//                bookView();
//            });
//
//            dashBoardController.getAddBookButton().setOnAction(event -> {
//                addBook();
//            });

    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
    update();
    sendNotification(1000, currentUser.getId(), "Login Successfully!!!", 0);
    updateNotifications();

    double radius = 30; // Bán kính hình tròn (150 / 2)
    Circle clipAvatar = new Circle(30, 30, radius); // Tâm (75, 75) với bán kính 75
    profileImage.setClip(clipAvatar);
    profileImage.setCursor(Cursor.HAND);  // Set the cursor to hand when hovering over profileImage
    profileImage.setOnMouseClicked(event -> {
      returnSetting(new ActionEvent());
    });

    try {
      // Lấy đường dẫn từ user (ví dụ từ currentUser.getAvatarLink())
      Image userImage = new Image(MainGUI.currentUser.getAvatarLink());
      profileImage.setImage(userImage); // Nếu tải được ảnh
    } catch (Exception e) {
      // Nếu không tải được ảnh, sử dụng ảnh mặc định "user.jpg"
      // Đảm bảo ảnh "user.jpg" nằm trong thư mục resources/images
      Image defaultImage = new Image(getClass().getResource("/images/user.jpg").toExternalForm());
      profileImage.setImage(defaultImage);
    }
  }
  /**
   * upd thong bao.
   */
  void updateNotifications() {
    numberNontifications.setText(String.valueOf(currentUser.getNotifications().size()));
  }

  private Label loadingLabel;
  private String query;
  private ArrayList<Book> result;

  /**
   * loading.
   */
  private void turnOnLoading() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingIndicator.fxml"));
      fxml = loader.load();

      LoadingController loadingIndicator = loader.getController();
      loadingLabel = loadingIndicator.getLoadingLabel();

      mainVbox.getChildren().clear();
      mainVbox.getChildren().setAll(fxml);

    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void updateProgressLabel(Task<ArrayList<Book>> task) {
    task.progressProperty().addListener((obs, oldValue, newValue) -> {
      int percent = (int) (newValue.doubleValue() * 100);
      loadingLabel.setText(percent + "%");
    });
  }

  /**
   * hien chi tiet sach.
   */
  public void returnDetailBook(Book currentBook, boolean apiMode) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookViewDetail.fxml"));
      fxml = loader.load();

      BookViewDetailController bookViewDetailController = loader.getController();

      mainVbox.getChildren().clear();
      mainVbox.getChildren().setAll(fxml);

      bookViewDetailController.setMainGUIController(this);
      bookViewDetailController.initialize(currentBook);
      bookViewDetailController.setApiMode(apiMode);
      bookViewDetailController.getReturnSearchBook().setOnAction(e -> {
        bookView();
      });
    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * them sach.
   */
  public void returnAddBook() {
    if(MainGUI.currentUser.getUserType().equals("user")) {
      sendNotification(1000, MainGUI.currentUser.getId(), "Functionality for admins only!!!!", 1);
      return;
    }
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
      fxml = loader.load();

      AddBookController addBookController = loader.getController();

      addBookController.setMainGUIController(this);
      mainVbox.getChildren().clear();
      mainVbox.getChildren().setAll(fxml);

    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * upd.
   */
  private void update() {
    reset();
    System.out.println(currentStage);
    switch (currentStage) {
      case 0:
        homeIcon.getStyleClass().add("icon-active-color");
        homeButton.getStyleClass().add("menu-btn-active");
        break;
      case 1:
        bookIcon.getStyleClass().add("icon-active-color");
        bookViewButton.getStyleClass().add("menu-btn-active");
        break;
      case 2:
        userIcon.getStyleClass().add("icon-active-color");
        userButton.getStyleClass().add("menu-btn-active");
        break;
      case 3:
        nontificationsIcon.getStyleClass().add("icon-active-color");
        nontificationsButton.getStyleClass().add("menu-btn-active");
        break;
      case 4:
        settingIcon.getStyleClass().add("icon-active-color");
        settingButton.getStyleClass().add("menu-btn-active");
        break;
      case 5:
        bookIssueIcon.getStyleClass().add("icon-active-color");
        bookIssueButton.getStyleClass().add("menu-btn-active");
        break;
    }
  }

  /**
   * reset.
   */
  private void reset() {
    // Clear the styles for all buttons and icons before applying new styles
    homeIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
    bookIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
    userIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
    nontificationsIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
    settingIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
    logOutIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
    bookIssueIcon.getStyleClass().removeAll("icon-active-color", "icon-color");

    homeButton.getStyleClass().removeAll("menu-btn-active", "parent");
    bookViewButton.getStyleClass().removeAll("menu-btn-active", "parent");
    userButton.getStyleClass().removeAll("menu-btn-active", "parent");
    nontificationsButton.getStyleClass().removeAll("menu-btn-active", "parent");
    settingButton.getStyleClass().removeAll("menu-btn-active", "parent");
    logOutButton.getStyleClass().removeAll("menu-btn-active", "parent");
    bookIssueButton.getStyleClass().removeAll("menu-btn-active", "parent");

    homeIcon.getStyleClass().add("icon-color");
    bookIcon.getStyleClass().add("icon-color");
    userIcon.getStyleClass().add("icon-color");
    nontificationsIcon.getStyleClass().add("icon-color");
    settingIcon.getStyleClass().add("icon-color");
    logOutIcon.getStyleClass().add("icon-color");
    bookIssueIcon.getStyleClass().add("icon-color");

    homeButton.getStyleClass().add("parent");
    bookViewButton.getStyleClass().add("parent");
    userButton.getStyleClass().add("parent");
    nontificationsButton.getStyleClass().add("parent");
    settingButton.getStyleClass().add("parent");
    logOutButton.getStyleClass().add("parent");
    bookIssueButton.getStyleClass().add("parent");
  }

  private void fadeAnimation() {
    FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), mainVbox);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(1.0);
    fadeIn.play();
  }

  /**
   * ve man hinh chinh.
   */
  @FXML
  private void returnHome() {
    currentStage = 0;
    Loading("DashBoard.fxml");
  }

  /**
   * xem sach.
   */
  @FXML
  public void bookView() {
    currentStage = 1;
    currentStage = 1;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookView.fxml"));

      fxml = loader.load();

      SearchBookController searchBookController = loader.getController();
      searchBookController.setMainGUIController(this);

      mainVbox.getChildren().removeAll();
      mainVbox.getChildren().setAll(fxml);


    } catch (IOException ex) {
      Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
    update();
  }

  /**
   * xem ng dung.
   */
  @FXML
  private void userView() {
    if (MainGUI.currentUser.getUserType().equals("user")) {
      sendNotification(1000, MainGUI.currentUser.getId(), "Functionality for admins only!!!!", 1);
      return;
    }
    currentStage = 2;
    Loading("UserView.fxml");
  }

  /**
   * mo setting.
   */
  @FXML
  public void returnSetting(ActionEvent actionEvent) {
    currentStage = 4;
    Loading("Setting.fxml");
    update();
  }


  public void returnNontifications(ActionEvent actionEvent) {
    currentStage = 3;
    Loading("Notifications.fxml");
  }

  @FXML
  public void closePopUp(ActionEvent actionEvent) {
    // Create a FadeTransition for the popUpPane
    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), popUpPane);
    fadeOut.setFromValue(1);  // Start fully visible
    fadeOut.setToValue(0);    // Fade to invisible

    // Set a delay before the fade-out effect starts, if needed, or remove it for immediate fade-out
    fadeOut.setDelay(
        Duration.millis(0));  // Set to 0 for immediate fade-out, adjust delay if needed

    // When the fade-out finishes, hide the popUpPane
    fadeOut.setOnFinished(event -> popUpPane.setVisible(false));

    // Play the fade-out animation
    fadeOut.play();
  }


  public void sendNotification(int senderId, int receiverId, String content, int type) {
    Notification newNotification = new Notification(senderId, receiverId, content, type);
    DBInfo.sendNotification(senderId, receiverId, content, type);

    if(showNotifications == false) return;

    if (currentUser.getId() != receiverId) {
      return;
    }

    if (type == 0) {
      imageNoti.setImage(new Image(getClass().getResource("/images/success.png").toExternalForm()));
    } else {
      imageNoti.setImage(new Image(getClass().getResource("/images/fail.png").toExternalForm()));
    }

    // Make the popUpPane visible and set initial opacity to 0
    popUpPane.setVisible(true);
    popUpPane.setOpacity(0);

    // Create Fade-in effect for the notification
    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), popUpPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    // Set initial position of the timeNotiStroke (progress bar) with endX at -100
    timeNotiStroke.setStartX(-100);   // Start position (off-screen)
    timeNotiStroke.setEndX(-100);     // Start with endX at -100 (invisible)

    // Define the total length (377) and the number of steps (50 steps for smoother animation)
    double totalLength = 377;
    int steps = 30; // Number of steps for smoother animation
    double stepLength = totalLength / steps;  // Calculate step length (377 / 50)

    // Create a Timeline to animate the length of the line (timeNotiStroke)
    Timeline timeline = new Timeline();

    // Create 50 keyframes to increment the length over 5 seconds
    for (int i = 1; i <= steps; i++) {
      final int step = i;
      // Each keyframe will occur at 0.1 second intervals (5 seconds divided by 50 steps)
      KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.1), e -> {
        // Update the endX position for each step
        timeNotiStroke.setEndX(-100 + step * stepLength);  // Increment endX smoothly
      });
      timeline.getKeyFrames().add(keyFrame);
    }

    timeline.setCycleCount(1);  // Run the timeline animation only once
    timeline.setAutoReverse(false);  // Disable auto-reverse, keep the final value at 277
    timeline.play();  // Start the timeline animation

    // Fade out the notification after 5 seconds (instead of 2 seconds)
    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), popUpPane);
    fadeOut.setFromValue(1);         // Start fully visible
    fadeOut.setToValue(0);           // Fade to invisible
    fadeOut.setDelay(Duration.millis(3000));  // Delay the fade-out by 5 seconds

    // When the fade-out finishes, hide the notification pane
    fadeOut.setOnFinished(event -> popUpPane.setVisible(false));

    // Play the animations: Fade-in, progress bar animation, then fade-out
    fadeIn.play();
    fadeOut.play();

    // Update the notification content
    labelNoti.setText("Notice from " + newNotification.getReceiver());
    contentNoti.setText(content);

    updateNotifications();
  }

  public void returnBookIssue(ActionEvent actionEvent) {
    currentStage = 5;

    Loading("BookIssue.fxml");
  }

  public void Loading(String fxmlName) {
    // Lấy Pane từ singleton của ProcessIndicator
    ProcessIndicator processIndicator = ProcessIndicator.getInstance();
    Pane loadingScreen = processIndicator.loadLoadingScreen();

    // Hiển thị progress pane trong mainVbox
    mainVbox.getChildren().clear();  // Xóa tất cả các thành phần hiện có trong VBox
    mainVbox.getChildren().add(loadingScreen);  // Thêm Pane loading vào mainVbox

    // Tạo và thực thi task trong một luồng riêng
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        try {
          // Mô phỏng thao tác lâu dài (ví dụ, tải FXML và thực hiện thao tác)
          FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
          fxml = loader.load();

          // Lấy đối tượng controller của FXML đã tải
          Object controller = loader.getController();

          // Dùng reflection để gọi phương thức setMainGUIController nếu nó tồn tại
          Method method = null;
          try {
            method = controller.getClass().getMethod("setMainGUIController", MainGUI.class);
            if (method != null) {
              method.invoke(controller, MainGUI.this);  // Gọi phương thức setMainGUIController
            }
          } catch (NoSuchMethodException e) {
            // Nếu không có phương thức setMainGUIController, ta sẽ không làm gì
            // Hoặc bạn có thể xử lý tùy theo yêu cầu, ví dụ: log một cảnh báo
          }

          // Thao tác với mainVbox
          Platform.runLater(() -> {
            mainVbox.getChildren().remove(loadingScreen);  // Ẩn progress pane
            mainVbox.getChildren().setAll(fxml);  // Thêm giao diện mới vào
          });

        } catch (IOException ex) {
          Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
      }
    };

    // Khi task hoàn thành, ẩn progress pane
    task.setOnSucceeded(event -> {
      mainVbox.getChildren().remove(loadingScreen);  // Ẩn progress pane khi hoàn thành
      update();
    });

    // Khi có lỗi trong task, ẩn progress pane
    task.setOnFailed(event -> {
      mainVbox.getChildren().remove(loadingScreen);  // Ẩn progress pane khi có lỗi
      Logger.getLogger(LoginController.class.getName())
          .log(Level.SEVERE, "Error occurred in task.", task.getException());
    });

    // Chạy task trong một luồng riêng
    Thread taskThread = new Thread(task);
    taskThread.setDaemon(true);  // Đảm bảo thread kết thúc khi ứng dụng đóng
    taskThread.start();
  }

}