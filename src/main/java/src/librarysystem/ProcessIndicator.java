package src.librarysystem;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ProcessIndicator {

  /**
   * Biến tĩnh duy nhất để lưu trữ thể hiện của Singleton
   */
  private static ProcessIndicator instance;

  /**
   * Phương thức getInstance() để lấy thể hiện duy nhất của ProcessIndicator
   */
  public static ProcessIndicator getInstance() {
    if (instance == null) {
      instance = new ProcessIndicator();
    }
    return instance;
  }

  /**
   *   Phương thức trả về một Pane chứa màn hình loading
    */
  public Pane loadLoadingScreen() {
    Pane root = new Pane();

    // Kích thước vòng tròn
    double radius = 100;
    double centerX = 590, centerY = 368;  // Thay đổi để căn giữa trong kích thước Pane 1180x736

    // Vòng tròn nền
    Circle backgroundCircle = new Circle(centerX, centerY, radius, Color.TRANSPARENT);
    backgroundCircle.setStroke(Color.LIGHTGRAY);
    backgroundCircle.setStrokeWidth(10);

    // Vòng tròn tiến trình
    Circle progressCircle = new Circle(centerX, centerY, radius, Color.TRANSPARENT);
    progressCircle.setStroke(Color.BLUE);
    progressCircle.setStrokeWidth(10);
    progressCircle.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

    // Thêm dash hiệu ứng
    double circumference = 2 * Math.PI * radius;
    progressCircle.getStrokeDashArray().addAll(circumference);
    progressCircle.setStrokeDashOffset(circumference);

    // Text phần trăm
    Text percentageText = new Text("0%");
    percentageText.setFont(Font.font(30));
    percentageText.setFill(Color.DARKBLUE);
    percentageText.setX(centerX - 30);  // Căn chỉnh lại vị trí
    percentageText.setY(centerY + 10);  // Căn chỉnh lại vị trí

    // Thêm phần trăm vào root
    root.getChildren().add(percentageText);

    // Hiệu ứng tiến trình
    Timeline progressTimeline = new Timeline(
        new KeyFrame(Duration.ZERO,
            new KeyValue(progressCircle.strokeDashOffsetProperty(), circumference)),
        new KeyFrame(Duration.seconds(2),
            new KeyValue(progressCircle.strokeDashOffsetProperty(), 0)),
        new KeyFrame(Duration.ZERO, new KeyValue(percentageText.textProperty(), "0%")),
        new KeyFrame(Duration.seconds(2), new KeyValue(percentageText.textProperty(), "100%"))
    );
    progressTimeline.setCycleCount(1);
    progressTimeline.play();

    // Hiệu ứng cập nhật phần trăm theo từng giây
    Timeline updatePercentageTimeline = new Timeline();
    int steps = 100;
    for (int i = 0; i <= steps; i++) {
      double progress = i;
      KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 3.0 / steps),
          new KeyValue(percentageText.textProperty(), (int) progress + "%"),
          new KeyValue(progressCircle.strokeDashOffsetProperty(),
              circumference - (progress / 100) * circumference));
      updatePercentageTimeline.getKeyFrames().add(keyFrame);
    }
    updatePercentageTimeline.setCycleCount(1);
    updatePercentageTimeline.play();

    // Hiệu ứng xoay viền
    Circle spinningBorder = new Circle(centerX, centerY, radius + 20, Color.TRANSPARENT);
    spinningBorder.setStroke(Color.LIGHTBLUE);
    spinningBorder.setStrokeWidth(4);
    spinningBorder.getStrokeDashArray().addAll(10d, 20d);

    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(8), spinningBorder);
    rotateTransition.setByAngle(360);
    rotateTransition.setCycleCount(Animation.INDEFINITE);
    rotateTransition.setInterpolator(Interpolator.LINEAR);
    rotateTransition.play();

    // Các điểm động xung quanh
    for (int i = 0; i < 12; i++) {
      double angle = i * 30 * Math.PI / 180; // Góc mỗi điểm
      double pointX = centerX + (radius + 30) * Math.cos(angle);
      double pointY = centerY + (radius + 30) * Math.sin(angle);

      Circle point = new Circle(pointX, pointY, 5, Color.DEEPSKYBLUE);
      ScaleTransition pointAnimation = new ScaleTransition(Duration.seconds(1.5), point);
      pointAnimation.setFromX(0);
      pointAnimation.setFromY(0);
      pointAnimation.setToX(1);
      pointAnimation.setToY(1);
      pointAnimation.setCycleCount(Animation.INDEFINITE);
      pointAnimation.setAutoReverse(true);
      pointAnimation.setDelay(Duration.seconds(i * 0.1));
      pointAnimation.play();

      root.getChildren().add(point);
    }

    // Thêm tất cả vào root
    root.getChildren().addAll(backgroundCircle, progressCircle, spinningBorder);

    return root;
  }
}
