package src.librarysystem;

import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UserController {
    public Button viewAllButton;
    public Button viewAdminButton;
    public Button viewUserButton;
    public VBox contentVBox;
    @FXML
    public VBox containerVbox;

    private ArrayList<User> userList = DBInfo.getUserList();
    @FXML
    private ScrollPane userScrollPane;

    // 0: View all user
    // 1: view admin
    // 2: view student
    private int filterMode = 0;

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

    private void update() {
        viewAllButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewAdminButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewUserButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        switch (filterMode) {
            case 0:
                viewAllButton.setStyle("-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
                break;
            case 1:
                viewAdminButton.setStyle("-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
                break;
            case 2:
                viewUserButton.setStyle("-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
                break;
        }
    }

    public void initialize() {
        displayUser();
    }

    private void displayUser() {
        VBox vbox = new VBox();
        vbox.setSpacing(0);
        vbox.setStyle("-fx-background-color: #FFFFFF;");
        for (User user : userList) {
            Pane userPane = createUserPane(user);
            vbox.getChildren().add(userPane);
        }

        userScrollPane.setContent(vbox);
    }

    public void viewAll(ActionEvent actionEvent) {
        if(filterMode == 0) return;
        filterMode = 0;
        update();
        userList = DBInfo.getUserList();
        displayUser();
    }

    public void viewAdmin(ActionEvent actionEvent) {
        if(filterMode == 1) return;
        filterMode = 1;
        update();
        userList = Filter.getInstance().getUserList("admin");
        displayUser();
    }

    public void viewUser(ActionEvent actionEvent) {
        if(filterMode == 2) return;
        filterMode = 2;
        update();
        userList = Filter.getInstance().getUserList("user");
        displayUser();
    }

    public void addNewUser(ActionEvent actionEvent) {
        GaussianBlur blurEffect = new GaussianBlur(10);
        containerVbox.setEffect(blurEffect); // Làm mờ phần nội dung chính

        // Tạo form tạo người dùng mới
        VBox addUserForm = new VBox();
        addUserForm.setStyle("-fx-background-color: #ffffff; -fx-border-color: #b9b3b3; -fx-padding: 20;");
        addUserForm.setPrefSize(400, 300);

        Label titleLabel = new Label("Add New User");
        titleLabel.setFont(new Font("System Bold", 16));
        titleLabel.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.setItems(FXCollections.observableArrayList("Admin", "User"));
        userTypeComboBox.setPromptText("Select User Type");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Lưu người dùng mới
            System.out.println("User added: " + nameField.getText());
            // Tắt hiệu ứng làm mờ và đóng form
            containerVbox.setEffect(null);
            containerVbox.getChildren().remove(addUserForm);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            // Tắt hiệu ứng làm mờ và đóng form
            containerVbox.setEffect(null);
            containerVbox.getChildren().remove(addUserForm);
        });

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        addUserForm.getChildren().addAll(titleLabel, nameField, usernameField, userTypeComboBox, buttonBox);
        addUserForm.setSpacing(15);

        // Đặt form ở trung tâm
        StackPane.setAlignment(addUserForm, Pos.CENTER);

        // Hiển thị form
        containerVbox.getChildren().add(addUserForm);
    }

    @FXML
    public void exportCSV(ActionEvent actionEvent) {
        // Lấy Stage từ ActionEvent
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Tạo đối tượng FileChooser để cho phép người dùng chọn vị trí và tên file
        FileChooser fileChooser = new FileChooser();

        // Đặt filter để chỉ hiển thị file Excel (.xlsx)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mở hộp thoại chọn file
        File selectedFile = fileChooser.showSaveDialog(stage);

        // Kiểm tra xem người dùng đã chọn file hay chưa
        if (selectedFile != null) {
            // Đảm bảo rằng file có phần mở rộng .xlsx nếu người dùng không nhập
            String fileName = selectedFile.getAbsolutePath();
            if (!fileName.endsWith(".xlsx")) {
                fileName += ".xlsx";
            }

            // Tạo một workbook Excel (sử dụng XSSFWorkbook cho định dạng .xlsx)
            Workbook workbook = new XSSFWorkbook();

            // Tạo một sheet trong workbook
            Sheet sheet = workbook.createSheet("Students");

            // Tạo tiêu đề cho bảng
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Email", "MSV", "University", "Phone", "Reputation"};

            // Tạo các cell cho tiêu đề
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Điền dữ liệu từ userList vào bảng Excel
            int rowNum = 1; // Dữ liệu bắt đầu từ hàng 2 (hàng đầu tiên là tiêu đề)
            for (User user : userList) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getName());
                row.createCell(2).setCellValue(user.getUsername());
                row.createCell(3).setCellValue(user.getMSV());
                row.createCell(4).setCellValue(user.getUniversity());
                row.createCell(5).setCellValue(user.getPhone());
                row.createCell(6).setCellValue(user.getReputation());
            }

            // Ghi workbook vào file được chọn
            try (FileOutputStream fileOut = new FileOutputStream(new File(fileName))) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("File Excel đã được xuất thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Lỗi khi xuất file Excel!");
            }
        } else {
            System.out.println("Không có file nào được chọn.");
        }
    }
}
