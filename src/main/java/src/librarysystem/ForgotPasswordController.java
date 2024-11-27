package src.librarysystem;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
      VBox mainVBox = getVbox();
      // Tạo giao diện loading
      VBox loadingVBox = SearchingProgressing.getInstance().createEmailSendingVbox();

      // Hiển thị giao diện loading vào mainVBox
      mainVBox.getChildren().clear(); // Xóa nội dung cũ
      mainVBox.getChildren().add(loadingVBox);

      // Tạo Task để gửi token
      Task<Boolean> sendTokenTask = new Task<>() {
        @Override
        protected Boolean call() {
          // Gửi email trong background thread
          return PasswordRecoveryService.sendToken(emailUser.getText());
        }
      };

      // Xử lý khi Task hoàn thành (gửi email xong)
      sendTokenTask.setOnSucceeded(workerStateEvent -> {
        boolean successSender = sendTokenTask.getValue(); // Kết quả từ sendToken

        // Cập nhật mainVBox sau khi hoàn thành
        mainVBox.getChildren().clear(); // Xóa giao diện loading

        if (successSender) {
          // Hiển thị giao diện thành công
          returnEmailSendCode(emailUser.getText());
        }
      });

      // Bắt đầu chạy Task trong một Thread
      new Thread(sendTokenTask).start();
    });

  }
}
