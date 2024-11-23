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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class UserController {
    public Button viewAllButton;
    public Button viewAdminButton;
    public Button viewUserButton;
    public VBox contentVBox;
    @FXML
    public TextField searchUserButton;
    @FXML
    public Label totalCustomers;
    @FXML
    public Label totalAdmins;
    @FXML
    public Label totalUsers;
    @FXML
    public Pane containerPane;
    @FXML
    public Pane tempPane;
    @FXML
    public ComboBox roleChoice;
    @FXML
    public TextField userNameAdd;
    @FXML
    public CheckBox markAll;

    private ArrayList<User> userList = Filter.getInstance().getUserList("ALL");
    @FXML
    private ScrollPane userScrollPane;

    // 0: View all user
    // 1: view admin
    // 2: view student
    private int filterMode = 0;

    private ArrayList<User> selectedUser = new ArrayList<>();

    private Pane createUserPane(User user, int index) {
        Pane pane = new Pane();
        pane.setPrefSize(1075, 71);

        // Simplify the border setting based on index
        String borderStyle = (index != userList.size() - 1 || index <= 3)
                ? "-fx-background-color: #f6f6f6; -fx-border-color: #b9b3b3; -fx-border-width: 0 0 1 0;"
                : "-fx-background-color: #f6f6f6; -fx-border-color: #b9b3b3; -fx-border-width: 0 0 0 0;";
        pane.setStyle(borderStyle);

        // CheckBox for marking user selection
        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutX(14);
        checkBox.setLayoutY(27);
        markUserList.add(checkBox);  // Assuming this is your list for managing checkboxes

        // Handle selection and deselection of users
        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                selectedUser.add(user);  // Add user to selected list when checked
            } else {
                selectedUser.remove(user);  // Remove user from selected list when unchecked
            }
        });

        // Labels for user information
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
        borrowedBooksLabel.setLayoutX(450);
        borrowedBooksLabel.setLayoutY(22);

        Label overdueBooksLabel = new Label(String.valueOf(user.getId()));
        overdueBooksLabel.setFont(new Font("System Bold", 16));
        overdueBooksLabel.setTextFill(Paint.valueOf("#f63434"));
        overdueBooksLabel.setLayoutX(621);
        overdueBooksLabel.setLayoutY(22);

        Label lastVisitedLabel = new Label(user.getUsername());
        lastVisitedLabel.setFont(new Font(14));
        lastVisitedLabel.setLayoutX(783);
        lastVisitedLabel.setLayoutY(22);

        // REMOVE Button (FontAwesome "REMOVE")
        Button removeButton = new Button("\uf00d");
        removeButton.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome'; -fx-font-size: 20px; -fx-cursor: hand;");
        removeButton.setLayoutX(960);
        removeButton.setLayoutY(18);

        // Action for REMOVE Button
        removeButton.setOnAction(event -> {
            if(!selectedUser.contains(user)) {
                selectedUser.add(user);
            }
            for (User x : selectedUser) {
                DBInfo.DeleteUser(x.getUsername());
                userList.remove(x);
            }
            update();
        });

        // WARNING Button (FontAwesome "WARNING")
        Button warningButton = new Button("\uf071");
        warningButton.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome'; -fx-font-size: 20px; -fx-cursor: hand;");
        warningButton.setLayoutX(1000);
        warningButton.setLayoutY(17);

        // Action for WARNING Button
        warningButton.setOnAction(event -> {
            if(!selectedUser.contains(user)) {
                selectedUser.add(user);
            }
            for (User x : selectedUser) {
                x.setBanned(true);
            }
        });

        // Add all elements to the pane
        pane.getChildren().addAll(checkBox, nameLabel, roleLabel, borrowedBooksLabel, overdueBooksLabel, lastVisitedLabel, removeButton, warningButton);

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

        int totalCustomer = Filter.getInstance().getUserList("ALL").size();
        int totalAdmin = Filter.getInstance().getUserList("admin").size();
        int totalUser = totalCustomer - totalAdmin;

        totalCustomers.setText(String.valueOf(totalCustomer));
        totalAdmins.setText(String.valueOf(totalAdmin));
        totalUsers.setText(String.valueOf(totalUser));

        displayUser();
    }

    private ArrayList<CheckBox> markUserList = new ArrayList<>();

    public void initialize() {
        int totalCustomer = Filter.getInstance().getUserList("ALL").size();
        int totalAdmin = Filter.getInstance().getUserList("admin").size();
        int totalUser = totalCustomer - totalAdmin;

        totalCustomers.setText(String.valueOf(totalCustomer));
        totalAdmins.setText(String.valueOf(totalAdmin));
        totalUsers.setText(String.valueOf(totalUser));

        markUserList.clear();
        displayUser();

        markAll.setOnAction(event -> {
            boolean isSelected = markAll.isSelected();
            for(CheckBox checkBox : markUserList) {
                checkBox.setSelected(isSelected);
            }
        });


    }

    private void displayUser() {
        VBox vbox = new VBox();
        vbox.setSpacing(0);
        vbox.setStyle("-fx-background-color: #FFFFFF;");
        int index = 0;
        for (User user : userList) {
            Pane userPane = createUserPane(user, index++);
            vbox.getChildren().add(userPane);
        }

        userScrollPane.setContent(vbox);
    }

    public void viewAll(ActionEvent actionEvent) {
        if(filterMode == 0) return;
        filterMode = 0;
        userList = Filter.getInstance().getUserList("ALL");
        update();
    }

    public void viewAdmin(ActionEvent actionEvent) {
        if(filterMode == 1) return;
        filterMode = 1;
        userList = Filter.getInstance().getUserList("admin");
        update();
    }

    public void viewUser(ActionEvent actionEvent) {
        if(filterMode == 2) return;
        filterMode = 2;
        userList = Filter.getInstance().getUserList("user");
        update();
    }

    public void addNewUser(ActionEvent actionEvent) {
        GaussianBlur blurEffect = new GaussianBlur(10);
        containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
        tempPane.setVisible(true);
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

    @FXML
    public void searchUser(ActionEvent actionEvent) {
        searchUserButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            String query = searchUserButton.getText();
            if (event.getCode().toString().equals("ENTER")) {
                if(query == null || query.isEmpty()) {
                    switch (filterMode) {
                        case 0:
                            userList = Filter.getInstance().getUserList("ALL");
                            break;
                        case 1:
                            userList = Filter.getInstance().getUserList("admin");
                            break;
                        case 2:
                            userList = Filter.getInstance().getUserList("user");
                            break;
                    }
                }
                else {
                    switch (filterMode) {
                        case 0:
                            userList = Filter.getInstance().getUserBySubstr(query, "ALL");
                            break;
                        case 1:
                            userList = Filter.getInstance().getUserBySubstr(query, "admin");
                            break;
                        case 2:
                            userList = Filter.getInstance().getUserBySubstr(query, "user");
                            break;
                    }
                }
            }
        });
        displayUser();
    }

    @FXML
    public void cancelAdd(ActionEvent actionEvent) {
        tempPane.setVisible(false);
        containerPane.setEffect(null);
        userNameAdd.clear();
    }

    @FXML
    public void submitAdd(ActionEvent actionEvent) {
        String username = userNameAdd.getText();
        if(username == null || username.isEmpty()) {
            System.out.println("Vui long nhap ten tai khoan");
            return;
        }
        if(!DBInfo.checkUnique(username)) {
            System.out.println("Tai khoan da ton tai");
            return;
        }
        User newUser = new User();
        newUser.setName("Account 000");
        newUser.setUsername(username);
        newUser.setPassword("1");
        newUser.setUserType("user");
        DBInfo.Register(0, "Account 000", username, "1", "user", null);
        userList.add(newUser);
        update();

        tempPane.setVisible(false);
        containerPane.setEffect(null);
        userNameAdd.clear();
    }
}
