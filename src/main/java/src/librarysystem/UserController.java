package src.librarysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Optional;

public class UserController {


    @FXML
    public TableView tableView;
    @FXML
    public TableColumn idColumn;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn statusColumn;
    @FXML
    public ImageView avatar;
    @FXML
    public TextField memberID;
    @FXML
    public TextField studentID;
    @FXML
    public TextField fullName;
    @FXML
    public TextField email;
    @FXML
    public TextField password;
    @FXML
    public AnchorPane rootContainer;

    private User currentUser = null;

    private boolean editMode = false;

    private ArrayList<User> userList = DBInfo.getUserList(); // Get the user list from DBInfo

    public void initialize() {
        ObservableList<User> users = FXCollections.observableArrayList(userList);

        // Cast TableView and TableColumns to their respective types
        tableView.setItems(users);

        // Set the cell value factories for the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentUser = (User) newValue;
                displayUserDetails((User) newValue);
            }
            editMode = false;
            update();
        });


        memberID.setEditable(false);
        rootContainer.setOnMousePressed(event -> {
            if (!tableView.isHover()) { // Nếu chuột không hover trên bảng
                currentUser = null;
            }
            memberID.clear();
            studentID.clear();
            fullName.clear();
            email.clear();
            password.clear();
            editMode = true;
            update();
            memberID.setEditable(false);
        });
    }

    private void displayUserDetails(User user) {
        memberID.setText(String.valueOf(user.getId()));
        studentID.setText(String.valueOf(user.getId()));
        email.setEditable(editMode);
        fullName.setText(user.getName());
        email.setText(user.getUsername());

        // Nếu bạn có hình ảnh (avatar), thêm logic để hiển thị ảnh
        if (user.getAvatarLink() != null) {
            avatar.setImage(new javafx.scene.image.Image(user.getAvatarLink()));
        } else {
            avatar.setImage(null); // Hoặc đặt hình ảnh mặc định
        }
        update();
    }

    private void update() {
        email.setEditable(editMode);
        fullName.setEditable(editMode);
        studentID.setEditable(editMode);
        memberID.setEditable(editMode);
        password.setEditable(editMode);
    }

    @FXML
    public void toggleEditMode(ActionEvent actionEvent) {
        editMode = !editMode;
        update();
    }

    @FXML
    public void removeUser(ActionEvent actionEvent) {
        if (currentUser != null) {
            // Tạo một hộp thoại xác nhận
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xác nhận xóa");
            confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa người dùng này?");
            confirmationAlert.setContentText("Tên người dùng: " + currentUser.getName());

            // Hiển thị hộp thoại và chờ người dùng chọn
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Nếu người dùng nhấn "OK", tiến hành xóa user
                DBInfo.DeleteUser(currentUser.getUsername());
                System.out.println("Người dùng đã được xóa!");

                // Xóa user khỏi danh sách và cập nhật bảng
                userList.remove(currentUser);
                tableView.getItems().remove(currentUser);
                currentUser = null; // Reset currentUser
            }
        } else {
            // Thông báo nếu chưa chọn user
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Lỗi");
            warningAlert.setHeaderText("Không có người dùng được chọn!");
            warningAlert.setContentText("Vui lòng chọn một người dùng trước khi xóa.");
            warningAlert.showAndWait();
        }
    }

    @FXML
    public void saveButton(ActionEvent actionEvent) {
        if(currentUser == null) {
            memberID.setEditable(false);
            int msv = Integer.valueOf(studentID.getText());
            String name = fullName.getText();
            String userName = email.getText();
            String passWord = password.getText();
            DBInfo.Register(msv, name, userName, passWord, "user");
        }
    }
}
