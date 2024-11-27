package src.librarysystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.control.Tooltip;

public class DashBoardController extends BaseController {

  @FXML
  public Label currentTime;
  public PieChart pieChart;
  public LineChart lineChart;
  public CategoryAxis dateX;
  public NumberAxis numberPersonY;
  public Label nameUser;
  public Label totalBooks;
  public Label totalUsers;
  public Label borrowedBooks;
  public Label overdueBooks;

  /**
   * Get percentage of the chart
   *
   * @param data chart
   * @return String percentage
   */
  private String getPercentage(PieChart.Data data) {
    double total = pieChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum();
    double percentage = (data.getPieValue() / total) * 100;
    return String.format("%.2f", percentage); // Format to 2 decimal places
  }

  /**
   * init the fxml
   */
  public void initialize() {
    updateCurrentTime();
    startClock();
    nameUser.setText(MainGUI.currentUser.getName());
    totalBooks.setText(String.valueOf(DBInfo.getBookList("ALL", "ALL", "ALL").size()));
    totalUsers.setText(String.valueOf(Filter.getInstance().getUserList("ALL").size()));
    borrowedBooks.setText(String.valueOf(DBInfo.countBorrowed()));
    overdueBooks.setText(String.valueOf(DBInfo.countOverdue()));

    ObservableList<PieChart.Data> pieChartData = ChartController.getPieChartData();
    if (pieChartData.isEmpty()) {
      System.out.println("No data found for PieChart.");
    } else {
      pieChart.setData(pieChartData);
      for (PieChart.Data data : pieChart.getData()) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(getPercentage(data) + "%");
        Tooltip.install(data.getNode(), tooltip);

        data.getNode().setOnMouseEntered(event -> {
          tooltip.setText(getPercentage(data) + "%");
        });
      }
    }
    ObservableList<XYChart.Data<String, Number>> weekData = ChartController.getLoginData();

    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Weekly Visitors");
    series.setData(weekData);
    dateX.setCategories(
        FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday"));
    lineChart.getData().add(series);
  }

  /**
   * start the clock
   */
  private void startClock() {
    Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateCurrentTime()));
    clock.setCycleCount(Timeline.INDEFINITE);
    clock.play();
  }

  /**
   * update current time
   */
  public void updateCurrentTime() {
    if (!MainGUI.timeFormat24h) {
      SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy | EEEE, hh:mm:ss a",
          Locale.getDefault());
      String currentTimeText = formatter.format(new Date());
      currentTime.setText(currentTimeText);
    } else {
      SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy | EEEE, HH:mm:ss",
          Locale.getDefault());
      String currentTimeText = formatter.format(new Date());
      currentTime.setText(currentTimeText);
    }

  }
}
