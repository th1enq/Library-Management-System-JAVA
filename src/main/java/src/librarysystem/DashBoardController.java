package src.librarysystem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.control.Tooltip;

public class DashBoardController {
    @FXML
    public Label currentTime;
    @FXML
    public PieChart pieChart;
    @FXML
    public LineChart lineChart;
    @FXML
    public CategoryAxis dateX;
    @FXML
    public NumberAxis numberPersonY;
    @FXML
    public Label nameUser;
    @FXML
    public Label totalBooks;
    @FXML
    public Label totalUsers;
    @FXML
    public Label borrowedBooks;
    @FXML
    private Button seeAllBook;

    @FXML
    private Button addBookButton;

    public Button getSeeAllBook() {
        return seeAllBook;
    }

    public Button getAddBookButton() {
        return addBookButton;
    }

    private String getPercentage(PieChart.Data data) {
        double total = pieChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum();
        double percentage = (data.getPieValue() / total) * 100;
        return String.format("%.2f", percentage); // Format to 2 decimal places
    }

    public void initialize() {
        updateCurrentTime(); // Initial time set
        startClock(); // Start the clock
        nameUser.setText(MainGUI.currentUser.getName());
        totalBooks.setText(String.valueOf(DBInfo.getBookList("ALL", "ALL", "ALL").size()));
        totalUsers.setText(String.valueOf(Filter.getInstance().getUserList("ALL").size()));
//        total

        PieChart.Data science = new PieChart.Data("Science", 10);
        PieChart.Data manga = new PieChart.Data("Manga", 20);
        PieChart.Data magazine = new PieChart.Data("Magazine", 40);
        PieChart.Data horror = new PieChart.Data("Horror", 20);
        PieChart.Data self_help = new PieChart.Data("Self_Help", 10);

        pieChart.getData().addAll(science, manga, magazine, horror, self_help);

        for (PieChart.Data data : pieChart.getData()) {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(getPercentage(data) + "%");
            Tooltip.install(data.getNode(), tooltip);

            // Update tooltip text on hover
            data.getNode().setOnMouseEntered(event -> {
                tooltip.setText(getPercentage(data) + "%");
            });
        }

        ObservableList<XYChart.Data<String, Number>> weekData = FXCollections.observableArrayList(
                new XYChart.Data<>("Monday", 20),
                new XYChart.Data<>("Tuesday", 10),
                new XYChart.Data<>("Wednesday", 5),
                new XYChart.Data<>("Thursday", 10),
                new XYChart.Data<>("Friday", 20),
                new XYChart.Data<>("Saturday", 20),
                new XYChart.Data<>("Sunday", 15)
        );

        // Create a series and add data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weekly Visitors");
        series.setData(weekData);

        // Set the categories for X-axis (days of the week)
        dateX.setCategories(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        // Add series to the line chart
        lineChart.getData().add(series);
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateCurrentTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    public void updateCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy | EEEE, hh:mm a", Locale.getDefault());
        String currentTimeText = formatter.format(new Date());
        currentTime.setText(currentTimeText);
    }
}
