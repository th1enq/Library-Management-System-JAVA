package src.librarysystem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class DashBoardController {
    @FXML
    public Label currentTime;
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

    public void initialize() {
        updateCurrentTime(); // Initial time set
        startClock(); // Start the clock
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.minutes(1), event -> updateCurrentTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    public void updateCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy | EEEE, hh:mm a", Locale.getDefault());
        String currentTimeText = formatter.format(new Date());
        currentTime.setText(currentTimeText);
    }
}
