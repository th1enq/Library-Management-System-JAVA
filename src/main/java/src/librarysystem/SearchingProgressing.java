package src.librarysystem;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SearchingProgressing {

  // Biến static để lưu đối tượng singleton
  private static SearchingProgressing instance;

  // Constructor private để ngăn việc khởi tạo trực tiếp
  private SearchingProgressing() {
  }

  // Phương thức getInstance để trả về đối tượng duy nhất của SearchingProgressing
  public static SearchingProgressing getInstance() {
    if (instance == null) {
      instance = new SearchingProgressing();
    }
    return instance;
  }

  public VBox createEmailSendingVbox() {
    // Tạo VBox chứa các phần tử
    VBox vbox = new VBox(20);
    vbox.setPrefSize(480, 480);
    vbox.setAlignment(Pos.CENTER);

    // Tạo vòng xoay với khoảng hở (một vòng tròn có khoảng trống)
    double radius = 50;
    Circle progressCircle = new Circle(radius, Color.TRANSPARENT);
    progressCircle.setStroke(Color.BLACK); // Đổi màu vòng tròn thành màu đen
    progressCircle.setStrokeWidth(6);
    progressCircle.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

    // Tạo khoảng trống trong vòng tròn, thu nhỏ khoảng trống
    double circumference = 2 * Math.PI * radius;
    progressCircle.getStrokeDashArray().addAll(circumference / 3); // Đặt khoảng trống nhỏ lại

    // Quay vòng tròn
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), progressCircle);
    rotateTransition.setByAngle(360); // Quay 1 vòng
    rotateTransition.setCycleCount(RotateTransition.INDEFINITE); // Quay liên tục
    rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
    rotateTransition.play();

    // Tạo tiêu đề "Đang tìm kiếm sách..."
    Text title = new Text("Please waiting ...");
    title.setFont(Font.font("Arial", 20));
    title.setFill(Color.BLACK); // Đổi màu tiêu đề thành đen

    // Tạo mô tả cho việc tìm kiếm
    Text description = new Text(
        "We are sending a confirmation code to your email.  Please make sure your email is correct !!!");
    description.setFont(Font.font("Arial", 14));
    description.setFill(Color.DARKGRAY);
    description.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    description.setWrappingWidth(300);

    // Tạo dấu chấm nhấp nhô
    Text dots = new Text("● ● ●");
    dots.setFont(Font.font("Arial", 20));
    dots.setFill(Color.BLACK); // Đổi màu dấu chấm thành màu đen

    // Tạo hiệu ứng nhấp nhô cho dấu chấm
    Timeline blinkTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0), e -> dots.setText("● ● ●")),
        new KeyFrame(Duration.seconds(0.4), e -> dots.setText("● ● ●")),
        new KeyFrame(Duration.seconds(0.8), e -> dots.setText("● ● ●"))
    );
    blinkTimeline.setCycleCount(Timeline.INDEFINITE);
    blinkTimeline.setAutoReverse(true);
    blinkTimeline.play();

    // Tạo hiệu ứng nhấp nhô cho dấu chấm
    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), dots);
    scaleTransition.setFromX(1);
    scaleTransition.setFromY(1);
    scaleTransition.setToX(1.5);
    scaleTransition.setToY(1.5);
    scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
    scaleTransition.setAutoReverse(true);
    scaleTransition.play();

    // Thêm tất cả các phần tử vào VBox
    vbox.getChildren().addAll(progressCircle, title, description, dots);

    return vbox;
  }

  // Phương thức tạo VBox chứa tất cả các phần tử
  public VBox createProgressingVBox() {
    // Tạo VBox chứa các phần tử
    VBox vbox = new VBox(20);
    vbox.setPrefSize(1035, 400);
    vbox.setAlignment(Pos.CENTER);

    // Tạo vòng xoay với khoảng hở (một vòng tròn có khoảng trống)
    double radius = 50;
    Circle progressCircle = new Circle(radius, Color.TRANSPARENT);
    progressCircle.setStroke(Color.BLACK); // Đổi màu vòng tròn thành màu đen
    progressCircle.setStrokeWidth(6);
    progressCircle.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

    // Tạo khoảng trống trong vòng tròn, thu nhỏ khoảng trống
    double circumference = 2 * Math.PI * radius;
    progressCircle.getStrokeDashArray().addAll(circumference / 3); // Đặt khoảng trống nhỏ lại

    // Quay vòng tròn
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), progressCircle);
    rotateTransition.setByAngle(360); // Quay 1 vòng
    rotateTransition.setCycleCount(RotateTransition.INDEFINITE); // Quay liên tục
    rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
    rotateTransition.play();

    // Tạo tiêu đề "Đang tìm kiếm sách..."
    Text title = new Text("Đang tìm kiếm sách...");
    title.setFont(Font.font("Arial", 20));
    title.setFill(Color.BLACK); // Đổi màu tiêu đề thành đen

    // Tạo mô tả cho việc tìm kiếm
    Text description = new Text(
        "Chúng tôi đang khám phá thư viện để tìm những cuốn sách phù hợp nhất với yêu cầu của bạn.\nVui lòng đợi trong giây lát.");
    description.setFont(Font.font("Arial", 14));
    description.setFill(Color.DARKGRAY);
    description.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    description.setWrappingWidth(300);

    // Tạo dấu chấm nhấp nhô
    Text dots = new Text("● ● ●");
    dots.setFont(Font.font("Arial", 20));
    dots.setFill(Color.BLACK); // Đổi màu dấu chấm thành màu đen

    // Tạo hiệu ứng nhấp nhô cho dấu chấm
    Timeline blinkTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0), e -> dots.setText("● ● ●")),
        new KeyFrame(Duration.seconds(0.4), e -> dots.setText("● ● ●")),
        new KeyFrame(Duration.seconds(0.8), e -> dots.setText("● ● ●"))
    );
    blinkTimeline.setCycleCount(Timeline.INDEFINITE);
    blinkTimeline.setAutoReverse(true);
    blinkTimeline.play();

    // Tạo hiệu ứng nhấp nhô cho dấu chấm
    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), dots);
    scaleTransition.setFromX(1);
    scaleTransition.setFromY(1);
    scaleTransition.setToX(1.5);
    scaleTransition.setToY(1.5);
    scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
    scaleTransition.setAutoReverse(true);
    scaleTransition.play();

    // Thêm tất cả các phần tử vào VBox
    vbox.getChildren().addAll(progressCircle, title, description, dots);

    return vbox;
  }
}
