package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Optional;

public class UserController {
    public Button viewAllButton;
    public Button viewAdminButton;
    public Button viewUserButton;
    public VBox contentVBox;

    private ArrayList<User> userList = DBInfo.getUserList();
    @FXML
    private ScrollPane userScrollPane;

    private Pane createUserPane(User user) {
        Pane pane = new Pane();
        pane.setPrefSize(1075, 71);
        pane.setStyle("-fx-background-color: #f6f6f6; -fx-border-color: #b9b3b3;");

        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutX(14);
        checkBox.setLayoutY(27);

        Label nameLabel = new Label(user.getName());
        nameLabel.setFont(new Font("System Bold", 14));
        nameLabel.setLayoutX(105);
        nameLabel.setLayoutY(17);

        Label roleLabel = new Label(user.getUserType());
        roleLabel.setAlignment(Pos.CENTER);
        roleLabel.setFont(new Font(14));
        roleLabel.setLayoutX(290);
        roleLabel.setLayoutY(14);
        roleLabel.setPrefSize(58, 41);
        roleLabel.setStyle("-fx-background-color: #d6e4ee; -fx-background-radius: 10px;");

        Label borrowedBooksLabel = new Label(String.valueOf(user.getId()));
        borrowedBooksLabel.setFont(new Font("System Bold", 16));
        borrowedBooksLabel.setTextFill(Paint.valueOf("#4eee4b"));
        borrowedBooksLabel.setLayoutX(481);
        borrowedBooksLabel.setLayoutY(22);

        Label overdueBooksLabel = new Label(String.valueOf(user.getId()));
        overdueBooksLabel.setFont(new Font("System Bold", 16));
        overdueBooksLabel.setTextFill(Paint.valueOf("#f63434"));
        overdueBooksLabel.setLayoutX(691);
        overdueBooksLabel.setLayoutY(22);

        Label lastVisitedLabel = new Label(user.getUsername());
        lastVisitedLabel.setFont(new Font(14));
        lastVisitedLabel.setLayoutX(839);
        lastVisitedLabel.setLayoutY(26);

//        Button actionButton = new Button();
//        actionButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
//        actionButton.setGraphic(new FontAwesomeIcon());
//        ac
//        actionButton.setLayoutX(1013);
//        actionButton.setLayoutY(23);

        pane.getChildren().addAll(checkBox, nameLabel, roleLabel, borrowedBooksLabel, overdueBooksLabel, lastVisitedLabel);
        return pane;
    }

    public void initialize() {
        VBox vbox = new VBox();
        vbox.setSpacing(0);
        vbox.setStyle("-fx-background-color: #FFFFFF;");

        for (User user : userList) {
            Pane userPane = createUserPane(user);
            vbox.getChildren().add(userPane);
        }

        userScrollPane.setContent(vbox);
    }

    private void populateScrollPane(ArrayList<User> users) {
        GridPane container = new GridPane();
        container.setHgap(20); // Khoảng cách giữa các cột
        container.setVgap(10); // Khoảng cách giữa các hàng
        container.setStyle("-fx-padding: 10;"); // Thêm khoảng cách bên trong

        // Thêm dòng tiêu đề
        Label nameTitle = new Label("Name");
        Label roleTitle = new Label("Role");
        Label borrowedBooksTitle = new Label("Borrowed Books");
        Label overdueBooksTitle = new Label("Overdue Books");
        Label lastVisitedTitle = new Label("Last Visited");
        Label actionTitle = new Label("Action");

        // Căn chỉnh các tiêu đề
        nameTitle.setStyle("-fx-font-weight: bold;");
        roleTitle.setStyle("-fx-font-weight: bold;");
        borrowedBooksTitle.setStyle("-fx-font-weight: bold;");
        overdueBooksTitle.setStyle("-fx-font-weight: bold;");
        lastVisitedTitle.setStyle("-fx-font-weight: bold;");
        actionTitle.setStyle("-fx-font-weight: bold;");

        // Thêm tiêu đề vào dòng đầu tiên
        container.add(nameTitle, 0, 0);
        container.add(roleTitle, 1, 0);
        container.add(borrowedBooksTitle, 2, 0);
        container.add(overdueBooksTitle, 3, 0);
        container.add(lastVisitedTitle, 4, 0);
        container.add(actionTitle, 5, 0);

        // Thêm dữ liệu người dùng
        int row = 1; // Bắt đầu từ hàng thứ hai (sau tiêu đề)
        for (User user : users) {
            Label nameLabel = new Label(user.getName());
            Label roleLabel = new Label(user.getUserType());
            Label borrowedBooksLabel = new Label(String.valueOf(user.getId()));
            Label overdueBooksLabel = new Label(String.valueOf(user.getId()));
            Label lastVisitedLabel = new Label(user.getUsername());

            // Tạo nút Action
            Button actionButton = new Button("Details");
            actionButton.setOnAction(e -> {
                System.out.println("View details for " + user.getName());
            });

            // Thêm các thành phần vào GridPane
            container.add(nameLabel, 0, row);
            container.add(roleLabel, 1, row);
            container.add(borrowedBooksLabel, 2, row);
            container.add(overdueBooksLabel, 3, row);
            container.add(lastVisitedLabel, 4, row);
            container.add(actionButton, 5, row);

            row++;
        }

        // Đặt GridPane vào ScrollPane
        userScrollPane.setContent(container);
    }


    public void viewAll(ActionEvent actionEvent) {
    }

    public void viewAdmin(ActionEvent actionEvent) {
    }

    public void viewUser(ActionEvent actionEvent) {
    }
}
