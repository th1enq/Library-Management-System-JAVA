package src.librarysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;

import java.io.IOException;

public class LibraryManagementSystem extends Application {
    public static Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        DBInfo.conn(); // Initialize database connection

        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
