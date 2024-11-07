package src.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class LoadingController {

    @FXML
    public Label loadingLabel;

    public void initialize() {

    }

    public Label getLoadingLabel() {
        return loadingLabel;
    }
}
