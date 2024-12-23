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

public class UserController extends BaseController {

  @FXML
  public Button viewAllButton;
  public Button viewAdminButton;
  public Button viewUserButton;
  public VBox contentVBox;
  public TextField searchUserButton;
  public Label totalCustomers;
  public Label totalAdmins;
  public Label totalUsers;
  public Pane containerPane;
  public Pane tempPane;
  public ComboBox roleChoice;
  public TextField userNameAdd;
  public CheckBox markAll;
  public Pane removeConfirm;
  public Button cancelDelete;
  public Button submitDelete;
  public Button submitBan;
  public Button cancelBan;
  public Pane banConfirm;
  public Pane unbanConfirm;
  public Button cancelUnban;
  public Button submitUnban;
  @FXML
  private ScrollPane userScrollPane;

  private ArrayList<User> userList = Filter.getInstance().getUserList("ALL");

  // 0: View all user
  // 1: view admin
  // 2: view student
  private int filterMode = 0;

  private ArrayList<User> selectedUser = new ArrayList<>();

  /**
   * khoi tao pane.
   */
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

    Label borrowedBooksLabel = new Label(String.valueOf(user.getUpcoming()));
    borrowedBooksLabel.setFont(new Font("System Bold", 16));
    borrowedBooksLabel.setTextFill(Paint.valueOf("#4eee4b"));
    borrowedBooksLabel.setLayoutX(450);
    borrowedBooksLabel.setLayoutY(22);

    Label overdueBooksLabel = new Label(String.valueOf(user.getOverdue()));
    overdueBooksLabel.setFont(new Font("System Bold", 16));
    overdueBooksLabel.setTextFill(Paint.valueOf("#f63434"));
    overdueBooksLabel.setLayoutX(621);
    overdueBooksLabel.setLayoutY(22);

    Label statusLabel = new Label(user.isBanned() ? "Banned" : "Actice");
    statusLabel.setLayoutX(783);
    statusLabel.setLayoutY(22);
    statusLabel.setPrefSize(72, 36);
    statusLabel.setAlignment(Pos.CENTER);
    statusLabel.setStyle("-fx-background-color: " + (!user.isBanned() ? "#00ff1e" : "#ff1e1e")
        + "; -fx-background-radius: 20px;");
    statusLabel.setFont(new Font("System Bold", 13));

    // REMOVE Button (FontAwesome "REMOVE")
    Button removeButton = new Button("\uf00d");
    removeButton.setStyle(
        "-fx-background-color: transparent; -fx-font-family: 'FontAwesome'; -fx-font-size: 20px; -fx-cursor: hand;");
    removeButton.setLayoutX(920);
    removeButton.setLayoutY(18);

    // Action for REMOVE Button
    removeButton.setOnAction(event -> {
      if (!selectedUser.contains(user)) {
        selectedUser.add(user);
      }
      removeSelectedUser();
    });

    // WARNING Button (FontAwesome "WARNING")
    Button warningButton = new Button("\uf071");
    warningButton.setStyle(
        "-fx-background-color: transparent; -fx-font-family: 'FontAwesome'; -fx-font-size: 20px; -fx-cursor: hand;");
    warningButton.setLayoutX(960);
    warningButton.setLayoutY(18);

    // Action for WARNING Button
    warningButton.setOnAction(event -> {
      if (!selectedUser.contains(user)) {
        selectedUser.add(user);
      }
      banSelectedUser();
    });

    Button unBan = new Button("\uf09c");
    unBan.setStyle(
        "-fx-background-color: transparent; -fx-font-family: 'FontAwesome'; -fx-font-size: 20px; -fx-cursor: hand;");
    unBan.setLayoutX(1000);
    unBan.setLayoutY(18);
    unBan.setOnAction(event -> {
      if (!selectedUser.contains(user)) {
        selectedUser.add(user);
      }
      unbanSelectedUser();
    });

    // Add all elements to the pane
    pane.getChildren()
        .addAll(checkBox, nameLabel, roleLabel, borrowedBooksLabel, overdueBooksLabel, statusLabel,
            removeButton, warningButton, unBan);

    return pane;
  }

  /**
   * xoa ng dung.
   */
  private void removeSelectedUser() {
    GaussianBlur blurEffect = new GaussianBlur(10);
    containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
    removeConfirm.setVisible(true);
  }
  /**
   * ban ng dung.
   */
  private void banSelectedUser() {
    GaussianBlur blurEffect = new GaussianBlur(10);
    containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
    banConfirm.setVisible(true);
  }

  private void unbanSelectedUser() {
    GaussianBlur blurEffect = new GaussianBlur(10);
    containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
    unbanConfirm.setVisible(true);
  }
  /**
   * update.
   */
  private void update() {
    viewAllButton.setStyle(
        "-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
    viewAdminButton.setStyle(
        "-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
    viewUserButton.setStyle(
        "-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
    switch (filterMode) {
      case 0:
        viewAllButton.setStyle(
            "-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
        userList = Filter.getInstance().getUserList("ALL");
        break;
      case 1:
        viewAdminButton.setStyle(
            "-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
        userList = Filter.getInstance().getUserList("admin");
        break;
      case 2:
        viewUserButton.setStyle(
            "-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
        userList = Filter.getInstance().getUserList("user");
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
  /**
   * khoi tao.
   */
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
      for (CheckBox checkBox : markUserList) {
        checkBox.setSelected(isSelected);
      }
    });

    roleChoice.getItems().addAll("User", "Admin");
    roleChoice.setValue("User");

    cancelDelete.setOnAction(event -> {
      removeConfirm.setVisible(false);
      containerPane.setEffect(null);
    });

    submitDelete.setOnAction(event -> {
      for (User x : selectedUser) {
        DBInfo.DeleteUser(x.getUsername());
      }
      sendNotification(1000, 999, "Delete successfully !!!", 0);
      update();
      removeConfirm.setVisible(false);
      containerPane.setEffect(null);
      selectedUser.clear();
    });

    cancelBan.setOnAction(event -> {
      banConfirm.setVisible(false);
      containerPane.setEffect(null);
    });

    submitBan.setOnAction(event -> {
      for (User x : selectedUser) {
        DBInfo.ban(x);

      }
      sendNotification(1000, 999, "Global ban successfully !!!", 0);
      update();
      banConfirm.setVisible(false);
      containerPane.setEffect(null);
      selectedUser.clear();
    });

    submitUnban.setOnAction(event -> {
      for (User x : selectedUser) {
        DBInfo.unBan(x);

      }
      sendNotification(1000, 999, " Unban successfully !!!", 0);
      update();
      unbanConfirm.setVisible(false);
      containerPane.setEffect(null);
      selectedUser.clear();
    });

    cancelUnban.setOnAction(event -> {
      unbanConfirm.setVisible(false);
      containerPane.setEffect(null);
    });
  }
  /**
   * hien ng dung.
   */
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
      if (filterMode == 0) {
          return;
      }
    filterMode = 0;
    userList = Filter.getInstance().getUserList("ALL");
    update();
  }

  public void viewAdmin(ActionEvent actionEvent) {
      if (filterMode == 1) {
          return;
      }
    filterMode = 1;
    userList = Filter.getInstance().getUserList("admin");
    update();
  }

  public void viewUser(ActionEvent actionEvent) {
      if (filterMode == 2) {
          return;
      }
    filterMode = 2;
    userList = Filter.getInstance().getUserList("user");
    update();
  }

  public void addNewUser(ActionEvent actionEvent) {
    GaussianBlur blurEffect = new GaussianBlur(10);
    containerPane.setEffect(blurEffect); // Làm mờ phần nội dung chính
    tempPane.setVisible(true);
  }
  /**
   * xuat file excel.
   */
  @FXML
  public void exportCSV(ActionEvent actionEvent) {
    // Lấy Stage từ ActionEvent
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

    // Tạo đối tượng FileChooser để cho phép người dùng chọn vị trí và tên file
    FileChooser fileChooser = new FileChooser();

    // Đặt filter để chỉ hiển thị file Excel (.xlsx)
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)",
        "*.xlsx");
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
        sendNotification(1000, 1000, "CSV file exported successfully", 1);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Lỗi khi xuất file Excel!");
      }
    } else {
      System.out.println("Không có file nào được chọn.");
    }
  }

  /**
   * tim user.
   */
  @FXML
  public void searchUser(ActionEvent actionEvent) {
    searchUserButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      String query = searchUserButton.getText();
      if (event.getCode().toString().equals("ENTER")) {
        if (query == null || query.isEmpty()) {
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
        } else {
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
  /**
   * huy them ng moi.
   */
  @FXML
  public void cancelAdd(ActionEvent actionEvent) {
    tempPane.setVisible(false);
    containerPane.setEffect(null);
    userNameAdd.clear();
  }
  /**
   * them ng dung.
   */
  @FXML
  public void submitAdd(ActionEvent actionEvent) {
    String username = userNameAdd.getText();
    if (username == null || username.isEmpty()) {
      System.out.println("Vui long nhap ten tai khoan");
      return;
    }
    if (!DBInfo.checkUnique(username)) {
      System.out.println("Tai khoan da ton tai");
      return;
    }
    User newUser = new User();
    newUser.setName("Account 000");
    newUser.setUsername(username);
    newUser.setPassword("1");
    newUser.setUserType(String.valueOf(roleChoice.getValue()));
    DBInfo.Register(0, "Account 000", username, "1", String.valueOf(roleChoice.getValue()), null);
    userList.add(newUser);
    update();

    tempPane.setVisible(false);
    containerPane.setEffect(null);
    userNameAdd.clear();

    sendNotification(1000, 1000, "Account registration successfully !!!", 1);
  }
}
