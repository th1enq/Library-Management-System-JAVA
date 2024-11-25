package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class BookIssueController extends BaseController {

    @FXML
    public StackPane stackPane;
    @FXML
    public Pane containerPane;
    @FXML
    public Button viewDelay;
    @FXML
    public Button viewReturned;
    @FXML
    public Button viewBorrowed;
    @FXML
    public Button viewPending;
    @FXML
    public Button viewAll;
    @FXML
    public Button viewLate;
    @FXML
    public Button sortID;
    @FXML
    public Button sortUserName;
    @FXML
    public Button sortTitle;
    @FXML
    public Button sortAuthor;
    @FXML
    public Button sortIssueDate;
    @FXML
    public Button sortReturnDate;

    // Track sorting states for each category
    private boolean isIDAscending = true;
    private boolean isUserNameAscending = true;
    private boolean isTitleAscending = true;
    private boolean isAuthorAscending = true;
    private boolean isIssueDateAscending = true;
    private boolean isReturnDateAscending = true;

    private int currentMode = 0;

    private int currentPage = 0; // Current page
    private static final int ITEMS_PER_PAGE = 4; // Max items per page
    private ArrayList<ArrayList<BookIssue>> paginatedBookIssueList; // Paginated book issues
    private ArrayList<BookIssue> bookIssueList = BookIssueDB.getTotalList(); // Default book issue list

    public void initialize() {
        // Initial display
        paginatedBookIssueList = paginateBookIssueList(bookIssueList);
        displayPage(currentPage);

        // Set button actions
        viewAll.setOnAction(event -> handleCategorySelection(BookIssueDB.getTotalList(), viewAll, 0));
        viewBorrowed.setOnAction(event -> handleCategorySelection(BookIssueDB.getBorrowedList(), viewBorrowed, 1));
        viewReturned.setOnAction(event -> handleCategorySelection(BookIssueDB.getReturnedList(), viewReturned, 2));
        viewDelay.setOnAction(event -> handleCategorySelection(BookIssueDB.getDelayList(), viewDelay, 3));
        viewPending.setOnAction(event -> handleCategorySelection(BookIssueDB.getPendingList(), viewPending, 4));
        viewLate.setOnAction(event -> handleCategorySelection(BookIssueDB.getLateList(), viewLate, 5));

        sortID.setOnAction(event -> {
            resetSortOrderExcept("ID");
            if (isIDAscending) {
                bookIssueList.sort(Comparator.comparingInt(BookIssue::getUserId));
            } else {
                bookIssueList.sort(Comparator.comparingInt(BookIssue::getUserId).reversed());
            }
            isIDAscending = !isIDAscending;
            paginatedBookIssueList = paginateBookIssueList(bookIssueList);
            currentPage = 0;
            displayPage(currentPage);
        });

// Sort by UserName
        sortUserName.setOnAction(event -> {
            resetSortOrderExcept("UserName");
            if (isUserNameAscending) {
                bookIssueList.sort(Comparator.comparing(BookIssue::getUsername, String.CASE_INSENSITIVE_ORDER));
            } else {
                bookIssueList.sort(Comparator.comparing(BookIssue::getUsername, String.CASE_INSENSITIVE_ORDER).reversed());
            }
            isUserNameAscending = !isUserNameAscending;
            paginatedBookIssueList = paginateBookIssueList(bookIssueList);
            currentPage = 0;
            displayPage(currentPage);
        });

// Sort by Title
        sortTitle.setOnAction(event -> {
            resetSortOrderExcept("Title");
            if (isTitleAscending) {
                bookIssueList.sort(Comparator.comparing(BookIssue::getBookTitle, String.CASE_INSENSITIVE_ORDER));
            } else {
                bookIssueList.sort(Comparator.comparing(BookIssue::getBookTitle, String.CASE_INSENSITIVE_ORDER).reversed());
            }
            isTitleAscending = !isTitleAscending;
            paginatedBookIssueList = paginateBookIssueList(bookIssueList);
            currentPage = 0;
            displayPage(currentPage);
        });

// Sort by Author
        sortAuthor.setOnAction(event -> {
            resetSortOrderExcept("Author");
            if (isAuthorAscending) {
                bookIssueList.sort(Comparator.comparing(BookIssue::getBookAuthor, String.CASE_INSENSITIVE_ORDER));
            } else {
                bookIssueList.sort(Comparator.comparing(BookIssue::getBookAuthor, String.CASE_INSENSITIVE_ORDER).reversed());
            }
            isAuthorAscending = !isAuthorAscending;
            paginatedBookIssueList = paginateBookIssueList(bookIssueList);
            currentPage = 0;
            displayPage(currentPage);
        });

// Sort by Issue Date
        sortIssueDate.setOnAction(event -> {
            resetSortOrderExcept("IssueDate");
            if (isIssueDateAscending) {
                bookIssueList.sort(Comparator.comparing(BookIssue::getIssueDate));
            } else {
                bookIssueList.sort(Comparator.comparing(BookIssue::getIssueDate).reversed());
            }
            isIssueDateAscending = !isIssueDateAscending;
            paginatedBookIssueList = paginateBookIssueList(bookIssueList);
            currentPage = 0;
            displayPage(currentPage);
        });

        // Sort by Return Date
        sortReturnDate.setOnAction(event -> {
            resetSortOrderExcept("ReturnDate");
            if (isReturnDateAscending) {
                bookIssueList.sort(Comparator.comparing(BookIssue::getReturnDate));
            } else {
                bookIssueList.sort(Comparator.comparing(BookIssue::getReturnDate).reversed());
            }
            isReturnDateAscending = !isReturnDateAscending;
            paginatedBookIssueList = paginateBookIssueList(bookIssueList);
            currentPage = 0;
            displayPage(currentPage);
        });
    }

    private void resetSortOrderExcept(String field) {
        if (!field.equals("ID")) isIDAscending = true;
        if (!field.equals("UserName")) isUserNameAscending = true;
        if (!field.equals("Title")) isTitleAscending = true;
        if (!field.equals("Author")) isAuthorAscending = true;
        if (!field.equals("IssueDate")) isIssueDateAscending = true;
        if (!field.equals("ReturnDate")) isReturnDateAscending = true;
    }

    private void update() {
        switch (currentMode) {
            case 0:
                bookIssueList = BookIssueDB.getTotalList();
                break;
            case 1:
                bookIssueList = BookIssueDB.getBorrowedList();
                break;
            case 2:
                bookIssueList = BookIssueDB.getReturnedList();
                break;
            case 3:
                bookIssueList = BookIssueDB.getDelayList();
                break;
            case 4:
                bookIssueList = BookIssueDB.getPendingList();
                break;
            case 5:
                bookIssueList = BookIssueDB.getLateList();
                break;
        }
        paginatedBookIssueList = paginateBookIssueList(bookIssueList);
        currentPage = Math.min(currentPage, paginatedBookIssueList.size() - 1);
        displayPage(currentPage);
    }

    private void handleCategorySelection(ArrayList<BookIssue> list, Button selectedButton, int mode) {
        resetCategoryButtonStyles();
        selectedButton.setStyle("-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
        bookIssueList = list;
        paginatedBookIssueList = paginateBookIssueList(bookIssueList);
        currentPage = 0; // Reset to the first page
        displayPage(currentPage);
        currentMode = mode;
    }

    private void resetCategoryButtonStyles() {
        viewAll.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewBorrowed.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewDelay.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewPending.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewReturned.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewLate.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
    }

    private ArrayList<ArrayList<BookIssue>> paginateBookIssueList(ArrayList<BookIssue> bookIssueList) {
        ArrayList<ArrayList<BookIssue>> pages = new ArrayList<>();
        for (int i = 0; i < bookIssueList.size(); i += ITEMS_PER_PAGE) {
            pages.add(new ArrayList<>(bookIssueList.subList(i, Math.min(i + ITEMS_PER_PAGE, bookIssueList.size()))));
        }
        return pages;
    }

    private void displayPage(int pageIndex) {
        containerPane.getChildren().clear();

        if (pageIndex < 0 || pageIndex >= paginatedBookIssueList.size()) return;

        ArrayList<BookIssue> currentBooks = paginatedBookIssueList.get(pageIndex);
        double layoutY = 0;

        for (BookIssue book : currentBooks) {
            Pane bookPane = createBookIssuePane(book);
            bookPane.setLayoutY(layoutY);
            layoutY += 70; // Spacing between panes
            containerPane.getChildren().add(bookPane);
        }

        addNavigationButtons();
    }

    private void addNavigationButtons() {
        // Left navigation button
        if (currentPage > 0) {
            Button leftButton = new Button();
            leftButton.setLayoutX(1007);
            leftButton.setLayoutY(275);
            leftButton.setPrefSize(30, 30);
            leftButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

            FontAwesomeIcon leftIcon = new FontAwesomeIcon();
            leftIcon.setGlyphName("ANGLE_LEFT");
            leftIcon.setSize("2em");

            leftButton.setGraphic(leftIcon);
            leftButton.setOnAction(event -> navigatePage(-1));
            containerPane.getChildren().add(leftButton);
        }

        // Right navigation button
        if (currentPage < paginatedBookIssueList.size() - 1) {
            Button rightButton = new Button();
            rightButton.setLayoutX(1070);
            rightButton.setLayoutY(275);
            rightButton.setPrefSize(30, 30);
            rightButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

            FontAwesomeIcon rightIcon = new FontAwesomeIcon();
            rightIcon.setGlyphName("ANGLE_RIGHT");
            rightIcon.setSize("2em");

            rightButton.setGraphic(rightIcon);
            rightButton.setOnAction(event -> navigatePage(1));
            containerPane.getChildren().add(rightButton);
        }
    }

    private void navigatePage(int direction) {
        int nextPage = currentPage + direction;
        if (nextPage >= 0 && nextPage < paginatedBookIssueList.size()) {
            currentPage = nextPage;
            displayPage(currentPage);
        }
    }

    public Pane createBookIssuePane(BookIssue book) {
        Pane pane = new Pane();
        pane.setPrefSize(1136, 65);

        Label userIdLabel = createLabel(String.valueOf(book.getUserId()), 36, 25, 100, 30);
        Label userEmailLabel = createLabel(book.getUsername(), 115, 25, 150, 30);
        Label bookTitleLabel = createLabel(book.getBookTitle(), 299, 25, 150, 30);
        Label bookAuthorLabel = createLabel(book.getBookAuthor(), 460, 25, 150, 30);
        Label issueDateLabel = createLabel(book.getIssueDate().toString(), 642, 25, 150, 30);
        Label returnDateLabel = createLabel(book.getReturnDate().toString(), 845, 25, 150, 30);

        if (book.getStatus().equalsIgnoreCase("Returned")) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("Delete");

            deleteItem.setOnAction(event -> {
                System.out.println("remove");
            });

            contextMenu.getItems().add(deleteItem);

            pane.setOnContextMenuRequested(event -> {
                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
            });
        }

        if (book.getStatus().equalsIgnoreCase("Pending")) {
            Button checkButton = new Button();
            checkButton.setLayoutX(1032);
            checkButton.setLayoutY(19);
            checkButton.setPrefSize(30, 30);
            checkButton.setStyle("-fx-background-color: #62f270; -fx-background-radius: 50px; -fx-cursor: hand;");
            FontAwesomeIcon checkIcon = new FontAwesomeIcon();
            checkIcon.setGlyphName("CHECK");
            checkIcon.setSize("1.2em");
            checkButton.setGraphic(checkIcon);
            // Set an action for the "Check" button (add appropriate handler)
            checkButton.setOnAction(event -> {
                MainGUI.currentUser.acceptBorrowRequest(book);
                update();
            });

            Button denyButton = new Button();
            denyButton.setLayoutX(1079);
            denyButton.setLayoutY(19);
            denyButton.setPrefSize(30, 30);
            denyButton.setStyle("-fx-background-color: #ff4d4d; -fx-background-radius: 50px; -fx-cursor: hand;");
            FontAwesomeIcon denyIcon = new FontAwesomeIcon();
            denyIcon.setGlyphName("TIMES");
            denyIcon.setSize("1.2em");
            denyButton.setGraphic(denyIcon);
            // Set an action for the "Deny" button (add appropriate handler)
            denyButton.setOnAction(event -> {
                MainGUI.currentUser.denyBorrowRequest(book);
                update();
            });
            pane.getChildren().addAll(checkButton, denyButton);
        }

        else {
            Label statusLabel = new Label(book.getStatus());
            statusLabel.setLayoutX(1037);
            statusLabel.setLayoutY(15);
            statusLabel.setPrefSize(72, 36);
            statusLabel.setAlignment(Pos.CENTER);
            statusLabel.setStyle("-fx-background-color: " + (book.getStatus().equalsIgnoreCase("Returned") ? "#00ff1e" : "#ff1e1e") + "; -fx-background-radius: 20px;");
            statusLabel.setFont(new Font("System Bold", 13));
            pane.getChildren().add(statusLabel);
        }

        Line line = new Line(0, 65, 1136, 65);

        pane.getChildren().addAll(userIdLabel, userEmailLabel, bookTitleLabel, bookAuthorLabel, issueDateLabel, returnDateLabel, line);
        return pane;
    }

    private Label createLabel(String text, double x, double y, double width, double height) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setPrefSize(width, height);
        label.setFont(new Font("System Bold", 13));
        label.setAlignment(Pos.CENTER);
        return label;
    }

    @FXML
    public void exportCSV(ActionEvent actionEvent) {
        // Lấy Stage từ ActionEvent
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Tạo đối tượng FileChooser để cho phép người dùng chọn vị trí và tên file
        FileChooser fileChooser = new FileChooser();

        // Đặt filter để chỉ hiển thị file CSV (*.csv)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mở hộp thoại chọn file
        File selectedFile = fileChooser.showSaveDialog(stage);

        // Kiểm tra xem người dùng đã chọn file hay chưa
        if (selectedFile != null) {
            // Đảm bảo rằng file có phần mở rộng .csv nếu người dùng không nhập
            String fileName = selectedFile.getAbsolutePath();
            if (!fileName.endsWith(".csv")) {
                fileName += ".csv";
            }

            // Tạo file CSV và ghi dữ liệu vào đó
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                // Tạo tiêu đề cho bảng
                String header = "User ID,Username,Book Title,Book Author,Issue Date,Return Date,Status";
                writer.write(header);
                writer.newLine(); // thêm dòng mới sau tiêu đề

                // Điền dữ liệu từ bookIssueList vào file CSV
                for (BookIssue book : bookIssueList) {
                    String row = book.getUserId() + "," +
                            book.getUsername() + "," +
                            book.getBookTitle() + "," +
                            book.getBookAuthor() + "," +
                            book.getIssueDate() + "," +
                            book.getReturnDate() + "," +
                            book.getStatus();
                    writer.write(row);
                    writer.newLine(); // thêm dòng mới sau mỗi hàng dữ liệu
                }

                System.out.println("File CSV đã được xuất thành công!");
                sendNotification(1000, 1000, "Xuất file CSV thành công");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Lỗi khi xuất file CSV!");
            }
        } else {
            System.out.println("Không có file nào được chọn.");
        }
    }

}
