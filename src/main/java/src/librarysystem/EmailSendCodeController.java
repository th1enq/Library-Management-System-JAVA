package src.librarysystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class EmailSendCodeController extends BaseController {
    public Button submitCode;
    public Button backtoLogin;
    public Label userEmail;
    public TextField firstNum;
    public TextField secondNum;
    public TextField thirdNum;
    public TextField fourthNum;
    public Button resendCode;
    public Label timeOutTryAgain;

    public void setEmail(String email) {
        userEmail.setText(email);
    }

    public void initialize() {
        // Xử lý nút quay lại màn hình đăng nhập
        backtoLogin.setOnAction(event -> returnSignIn());

        // Thiết lập logic cho từng ô TextField
        setupCodeInput(firstNum, null, secondNum);
        setupCodeInput(secondNum, firstNum, thirdNum);
        setupCodeInput(thirdNum, secondNum, fourthNum);
        setupCodeInput(fourthNum, thirdNum, null);

        // Focus vào ô đầu tiên khi khởi tạo
        firstNum.requestFocus();

        submitCode.setOnAction(actionEvent -> {
            StringBuilder emailCode = new StringBuilder();
            emailCode.append(firstNum.getText());
            emailCode.append(secondNum.getText());
            emailCode.append(thirdNum.getText());
            emailCode.append(fourthNum.getText());
            if(PasswordRecoveryService.checkToken(userEmail.getText(), emailCode.toString())) {
                System.out.println("Juan !!!");
                returnPasswordReset(userEmail.getText());
            }
            else {
                System.out.println("Deo juan !!!");
            }
        });

        resendCode.setOnAction(event -> {
            boolean successSender = PasswordRecoveryService.sendToken(userEmail.getText());
            if (successSender) {
                resendCode.setVisible(false);
                timeOutTryAgain.setVisible(true);

                // Khởi tạo đồng hồ đếm ngược
                final int[] countdown = {30}; // Đếm ngược 30 giây
                Timeline timeline = new Timeline(); // Khởi tạo timeline trước
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), action -> {
                    countdown[0]--; // Giảm số giây còn lại
                    timeOutTryAgain.setText("Try again in " + countdown[0] + "s...");

                    if (countdown[0] <= 0) {
                        // Khi hết thời gian, hiển thị lại nút resendCode và ẩn timeOutTryAgain
                        timeline.stop();
                        resendCode.setVisible(true);
                        timeOutTryAgain.setVisible(false);
                    }
                }));
                timeline.setCycleCount(countdown[0]); // Lặp lại theo số giây
                timeline.play(); // Bắt đầu đếm ngược
            }
        });
    }

    private void setupCodeInput(TextField currentField, TextField prevField, TextField nextField) {
        currentField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #E6E7EB; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");

        currentField.setOnKeyTyped(event -> {
            String input = event.getCharacter();
            if (!input.matches("\\d")) {
                event.consume(); // Không cho phép nhập ký tự không phải số
            } else {
                currentField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #748FD2; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");
                if (nextField != null) {
                    nextField.requestFocus(); // Chuyển sang ô tiếp theo nếu có
                }
            }
        });

        currentField.setOnKeyReleased(event -> {
            if (currentField.getText().isEmpty()) {
                currentField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #E6E7EB; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");
            }
        });

        currentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                currentField.setText(newValue.substring(0, 1)); // Giới hạn 1 ký tự
            }
        });

        // Xử lý khi nút xóa (Backspace) được nhấn
        currentField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                    if (prevField != null && currentField.getText().isEmpty()) {
                        // Quay lại ô trước nếu có
                        prevField.requestFocus();
                        prevField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #E6E7EB; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");
                    }
                    // Khôi phục màu cho ô hiện tại nếu bị xóa
                    if (currentField.getText().isEmpty()) {
                        currentField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #E6E7EB; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");
                    }
                    break;
                default:
                    break;
            }
        });
    }
}
