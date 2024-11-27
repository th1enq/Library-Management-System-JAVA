package src.librarysystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    @FXML
    public Pane mainPane;
    @FXML
    public Button nextTopList;
    public StackPane userStackPane;
    public Button sortReturnDateUser;
    public Button sortIssueDateUser;
    public Button sortAuthorUser;
    public Button sortTitleUser;
    public Button viewReturnedUser;
    public Button viewBorrowedUser;
    public Button viewPendingUser;
    public Button viewAllUser;
    public Pane userFilterPane;
    public Pane userTabel;
    public Pane adminFilterPane;
    public Pane adminTable;
    public Pane containerPaneUser;

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
    private ArrayList<BookIssue> bookIssueList = new ArrayList<>(); // Default book issue list

    private Pane topListPane1;
    private Pane topListPane2;

    public void initialize() {
        if(MainGUI.currentUser.getUserType().equals("admin")) {
            initForAdmin();
        }
        else {
            initForUser();
        }
        nextTopList.setOnAction(event -> {
            currentTopList = (currentTopList + 1) % topList.size();
            mainPane.getChildren().remove(topListPane1);
            mainPane.getChildren().remove(topListPane2);
            displayTopChoice();
        });
        displayTopChoice();
    }

    private void handleCategorySelectionUser(ArrayList<BookIssue> list, Button selectedButton, int mode) {
        resetCategoryButtonStylesUser();
        selectedButton.setStyle("-fx-background-color: #fff; -fx-background-radius: 5px; -fx-cursor: hand;");
        bookIssueList = list;
        paginatedBookIssueList = paginateBookIssueList(bookIssueList);
        currentPage = 0; // Reset to the first page
        displayPage(currentPage);
        currentMode = mode;
    }

    private void resetCategoryButtonStylesUser() {
        viewAllUser.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewBorrowedUser.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewPendingUser.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewReturnedUser.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px; -fx-cursor: hand;");
    }

    private void initForUser() {
        userFilterPane.setVisible(true);
        userTabel.setVisible(true);
        userStackPane.setVisible(true);

        adminFilterPane.setVisible(false);
        stackPane.setVisible(false);
        adminTable.setVisible(false);

        bookIssueList = BookIssueDB.getTotalListByUserId(MainGUI.currentUser.getId());
        // Initial display
        paginatedBookIssueList = paginateBookIssueList(bookIssueList);
        displayPage(currentPage);

        // Set button actions
        viewAllUser.setOnAction(event -> handleCategorySelectionUser(BookIssueDB.getTotalListByUserId(MainGUI.currentUser.getId()), viewAllUser, 0));
        viewBorrowedUser.setOnAction(event -> handleCategorySelectionUser(BookIssueDB.getBorrowedListByUserId(MainGUI.currentUser.getId()), viewBorrowedUser, 1));
        viewReturnedUser.setOnAction(event -> handleCategorySelectionUser(BookIssueDB.getReturnedListByUserId(MainGUI.currentUser.getId()), viewReturnedUser, 2));
        viewPendingUser.setOnAction(event -> handleCategorySelectionUser(BookIssueDB.getPendingListByUserId(MainGUI.currentUser.getId()), viewPendingUser, 4));

        sortTitleUser.setOnAction(event -> {
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

        sortAuthorUser.setOnAction(event -> {
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

        sortIssueDateUser.setOnAction(event -> {
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
        sortReturnDateUser.setOnAction(event -> {
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

    private void initForAdmin() {
        userFilterPane.setVisible(false);
        userTabel.setVisible(false);
        userStackPane.setVisible(false);

        adminFilterPane.setVisible(true);
        stackPane.setVisible(true);
        adminTable.setVisible(true);
        bookIssueList = BookIssueDB.getTotalList();
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

    private String getRatingStars(int rating, int maxRating) {
        StringBuilder stars = new StringBuilder();

        // Add filled stars
        for (int i = 0; i < rating; i++) {
            stars.append("\uf005"); // Unicode for filled star
        }

        // Add empty stars
        for (int i = rating; i < maxRating; i++) {
            stars.append("\uf006"); // Unicode for empty star
        }

        return stars.toString();
    }

    private Pane createBookPane1() {
        Book firstBook = topList.get(currentTopList);

        // Create first Pane with layout and styling
        Pane pane1 = new Pane();
        pane1.setLayoutX(214.0);
        pane1.setLayoutY(27.0);
        pane1.setPrefHeight(259.0);
        pane1.setPrefWidth(478.0);
        pane1.setStyle("-fx-border-color: #000; -fx-border-radius: 20; -fx-background-radius: 20;");

        // Create ImageView for the book cover
        ImageView imageView = new ImageView(firstBook.getThumbnail());
        imageView.setFitHeight(289.0);
        imageView.setFitWidth(211.0);
        imageView.setLayoutY(-17.0);

        // Create labels for title and author with bold font
        Label titleLabel = new Label(firstBook.getTitle());
        titleLabel.setLayoutX(238.0);
        titleLabel.setLayoutY(27.0);
        titleLabel.setFont(new Font("System Bold Italic", 16)); // Bold font using "System Bold"

        Label authorLabel = new Label(firstBook.getAuthors());
        authorLabel.setLayoutX(238.0);
        authorLabel.setLayoutY(55.0);
        authorLabel.setTextFill(Color.web("#656161"));
        authorLabel.setFont(new Font("System Bold Italic", 12)); // Bold font using "System Bold"

        // Create Button for details
        Button detailsButton = new Button("Xem chi tiết");
        detailsButton.setLayoutX(266.0);
        detailsButton.setLayoutY(209.0);
        detailsButton.setPrefHeight(36.0);
        detailsButton.setPrefWidth(105.0);
        detailsButton.setStyle("-fx-background-color: #000; -fx-background-radius: 20px; -fx-cursor: hand;");
        detailsButton.setTextFill(Color.web("#fefefe"));

        detailsButton.setOnAction(event -> {
            returnDetailBook(firstBook, false);
        });

        // Create views count and category labels
        Label viewsLabel = new Label(String.valueOf(firstBook.getNumView()) + " views");
        viewsLabel.setLayoutX(280.0);
        viewsLabel.setLayoutY(130.0);
        viewsLabel.setTextFill(Color.web("#a5a5a5"));
        viewsLabel.setFont(new Font("System Bold", 13)); // Bold font using "System Bold"

        Label ratingLabel = new Label(String.valueOf(firstBook.getRating()) + "/5");
        ratingLabel.setLayoutX(365.0);
        ratingLabel.setLayoutY(98.0);
        ratingLabel.setFont(new Font("System Bold", 13)); // Bold font using "System Bold"

        // Category label with bold font
        Label categoryLabel = new Label(firstBook.getCategory());
        categoryLabel.setAlignment(javafx.geometry.Pos.CENTER);
        categoryLabel.setLayoutX(238.0);
        categoryLabel.setLayoutY(169.0);
        categoryLabel.setPrefHeight(32.0);
        categoryLabel.setPrefWidth(60.0);
        categoryLabel.setStyle("-fx-background-color: A8D8FF; -fx-background-radius: 10px;");
        categoryLabel.setTextFill(Color.web("#080000"));
        categoryLabel.setFont(new Font("System Bold", 12)); // Bold font using "System Bold"

        // Rating stars
        String ratingStars = getRatingStars(firstBook.getRating(), 5); // Pass rating and max stars (5)

        // Create a Label to display the stars as text (using FontAwesome)
        Label starLabel = new Label(ratingStars);
        starLabel.setLayoutX(238); // Adjust positioning
        starLabel.setLayoutY(93);  // Adjust positioning
        starLabel.setStyle("-fx-font-family: 'FontAwesome'; -fx-text-fill: #FFD700; -fx-font-size: 20px;");

        FontAwesomeIcon eyeIcon = new FontAwesomeIcon();
        eyeIcon.setGlyphName("EYE");
        eyeIcon.setSize("1.2em");
        eyeIcon.setLayoutX(238);
        eyeIcon.setLayoutY(142);

        // Add all elements to the pane
        pane1.getChildren().addAll(imageView, titleLabel, authorLabel, detailsButton, viewsLabel, ratingLabel, categoryLabel, starLabel, eyeIcon);

        return pane1;
    }


    private Pane createBookPane2() {
        // Xác định sách thứ currentTopList + 1 (vòng lặp khi vượt quá danh sách)
        int nextIndex = (currentTopList + 1) % topList.size();
        Book secondBook = topList.get(nextIndex);

        // Tạo Pane thứ hai với bố cục và kiểu dáng nhỏ hơn
        Pane pane2 = new Pane();
        pane2.setLayoutX(761.0); // Vị trí ngang của pane
        pane2.setLayoutY(65.0); // Vị trí dọc của pane
        pane2.setPrefHeight(200.0); // Chiều cao của pane
        pane2.setPrefWidth(376.0);  // Chiều rộng của pane
        pane2.setStyle("-fx-border-color: #000; -fx-border-radius: 20; -fx-background-radius: 20;");

        // ImageView cho bìa sách
        ImageView imageView = new ImageView(secondBook.getThumbnail());
        imageView.setFitHeight(220.0);
        imageView.setFitWidth(130.0);
        imageView.setLayoutX(-2.0);
        imageView.setLayoutY(-5.0);

        // Label cho tiêu đề
        Label titleLabel = new Label(secondBook.getTitle());
        titleLabel.setLayoutX(150.0);
        titleLabel.setLayoutY(10.0);
        titleLabel.setFont(new Font("System Bold Italic", 14));

        // Label cho tác giả
        Label authorLabel = new Label(secondBook.getAuthors());
        authorLabel.setLayoutX(150.0);
        authorLabel.setLayoutY(35.0);
        authorLabel.setTextFill(Color.web("#656161"));
        authorLabel.setFont(new Font("System Italic", 12));

        // Label cho số lượt xem
        Label viewsLabel = new Label(String.valueOf(secondBook.getNumView()) + " views");
        viewsLabel.setLayoutX(175.0);
        viewsLabel.setLayoutY(100.0);
        viewsLabel.setFont(new Font("System Bold Italic", 12));
        viewsLabel.setTextFill(Color.web("#a5a5a5"));

        // Label cho đánh giá
        Label ratingLabel = new Label(String.valueOf(secondBook.getRating()) + "/5");
        ratingLabel.setLayoutX(250);
        ratingLabel.setLayoutY(70);
        ratingLabel.setFont(new Font("System Bold Italic", 12));

        // Icon đánh giá sao
        String ratingStars = getRatingStars(secondBook.getRating(), 5);
        Label starLabel = new Label(ratingStars);
        starLabel.setLayoutX(150.0);
        starLabel.setLayoutY(70);
        starLabel.setStyle("-fx-font-family: 'FontAwesome'; -fx-text-fill: #FFD700; -fx-font-size: 14px;");

        FontAwesomeIcon eyeIcon = new FontAwesomeIcon();
        eyeIcon.setGlyphName("EYE");
        eyeIcon.setSize("1em");
        eyeIcon.setLayoutX(150);
        eyeIcon.setLayoutY(113);

        Label categoryLabel = new Label(secondBook.getCategory());
        categoryLabel.setAlignment(javafx.geometry.Pos.CENTER);
        categoryLabel.setLayoutX(200);
        categoryLabel.setLayoutY(150);
        categoryLabel.setPrefHeight(20.0);
        categoryLabel.setPrefWidth(55.0);
        categoryLabel.setStyle("-fx-background-color: A8D8FF; -fx-background-radius: 10px;");
        categoryLabel.setTextFill(Color.web("#080000"));
        categoryLabel.setFont(new Font("System Bold", 11)); // Bold font using "System Bold"

        // Thêm tất cả các phần tử vào pane2
        pane2.getChildren().addAll(imageView, titleLabel, authorLabel, viewsLabel, ratingLabel, starLabel, eyeIcon, categoryLabel);

        return pane2;
    }



    private int currentTopList = 0;
    private ArrayList<Book> topList = DBInfo.getBookListByNumView();

    private void displayTopChoice() {
        topListPane1 = createBookPane1();
        topListPane2 = createBookPane2();
        mainPane.getChildren().addAll(topListPane1, topListPane2);
    }

    private void resetSortOrderExcept(String field) {
        if (!field.equals("ID")) isIDAscending = true;
        if (!field.equals("UserName")) isUserNameAscending = true;
        if (!field.equals("Title")) isTitleAscending = true;
        if (!field.equals("Author")) isAuthorAscending = true;
        if (!field.equals("IssueDate")) isIssueDateAscending = true;
        if (!field.equals("ReturnDate")) isReturnDateAscending = true;
    }

    private void updateForUser() {
        switch (currentMode) {
            case 0:
                bookIssueList = BookIssueDB.getTotalListByUserId(MainGUI.currentUser.getId());
                break;
            case 1:
                bookIssueList = BookIssueDB.getBorrowedListByUserId(MainGUI.currentUser.getId());
                break;
            case 2:
                bookIssueList = BookIssueDB.getReturnedListByUserId(MainGUI.currentUser.getId());
                break;
            case 4:
                bookIssueList = BookIssueDB.getPendingListByUserId(MainGUI.currentUser.getId());
                break;
        }
        paginatedBookIssueList = paginateBookIssueList(bookIssueList);
        currentPage = Math.min(currentPage, paginatedBookIssueList.size() - 1);
        displayPage(currentPage);
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
        containerPaneUser.getChildren().clear();
        containerPane.getChildren().clear();

        if (pageIndex < 0 || pageIndex >= paginatedBookIssueList.size()) return;

        ArrayList<BookIssue> currentBooks = paginatedBookIssueList.get(pageIndex);
        double layoutY = 0;

        for (BookIssue book : currentBooks) {
            Pane bookPane = MainGUI.currentUser.getUserType().equals("admin") ? createBookIssuePane(book) : createBookIssuePaneUser(book);
            bookPane.setLayoutY(layoutY);
            layoutY += 70; // Spacing between panes
            if(MainGUI.currentUser.getUserType().equals("admin")) {
                containerPane.getChildren().add(bookPane);
            }
            else {
                containerPaneUser.getChildren().add(bookPane);
            }
        }
        if(MainGUI.currentUser.getUserType().equals("admin")) {
            addNavigationButtonsForAdmin();
        }
        else {
            addNavigationButtonsForUser();
        }
    }

    private void addNavigationButtonsForUser() {
        // Left navigation button
        if (currentPage > 0) {
            Button leftButton = new Button();
            leftButton.setLayoutX(670);
            leftButton.setLayoutY(275);
            leftButton.setPrefSize(30, 30);
            leftButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

            FontAwesomeIcon leftIcon = new FontAwesomeIcon();
            leftIcon.setGlyphName("ANGLE_LEFT");
            leftIcon.setSize("2em");

            leftButton.setGraphic(leftIcon);
            leftButton.setOnAction(event -> navigatePage(-1));
            containerPaneUser.getChildren().add(leftButton);
        }

        // Right navigation button
        if (currentPage < paginatedBookIssueList.size() - 1) {
            Button rightButton = new Button();
            rightButton.setLayoutX(733);
            rightButton.setLayoutY(275);
            rightButton.setPrefSize(30, 30);
            rightButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

            FontAwesomeIcon rightIcon = new FontAwesomeIcon();
            rightIcon.setGlyphName("ANGLE_RIGHT");
            rightIcon.setSize("2em");

            rightButton.setGraphic(rightIcon);
            rightButton.setOnAction(event -> navigatePage(1));
            containerPaneUser.getChildren().add(rightButton);
        }
    }

    private void addNavigationButtonsForAdmin() {
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

    public Pane createBookIssuePaneUser(BookIssue book) {
        Pane pane = new Pane();
        pane.setPrefSize(795, 65);

        Label bookTitleLabel = createLabel(book.getBookTitle(), 39, 14, 100, 30);
        Label authorTileLabel = createLabel(book.getBookAuthor(), 167, 14, 150, 30);
        Label issueDateLabel = createLabel(book.getIssueDate().toString(), 337, 14, 150, 30);
        Label returnDateLabel = createLabel(book.getReturnDate().toString(), 510, 14, 150, 30);
        Label statusLabel = new Label(book.getStatus());

        if (book.getStatus().equalsIgnoreCase("Borrowed")) {
            Button returnButton = new Button("Return");
            returnButton.setLayoutX(700); // Đặt vị trí gần trạng thái
            returnButton.setLayoutY(14);
            returnButton.setPrefSize(80, 30);
            returnButton.setStyle("-fx-background-color: #ff4d4d; -fx-background-radius: 15px; -fx-text-fill: white; -fx-cursor: hand;");
            returnButton.setFont(new Font("System Bold", 13));
            returnButton.setOnAction(event -> {
                Book currentBook = DBInfo.getBook(book.getBookTitle());
                try {
                    MainGUI.currentUser.traSach(currentBook);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                updateForUser();
                sendNotification(1000, MainGUI.currentUser.getId(), " " + currentBook.getTitle() + " thành công !");
                sendNotification(MainGUI.currentUser.getId(), 999, "User " + MainGUI.currentUser.getId() + " trả sách " + currentBook.getTitle());
            });

            pane.getChildren().add(returnButton);
        }
        else {
            // Định dạng label trạng thái
            statusLabel.setLayoutX(697);
            statusLabel.setLayoutY(14);
            statusLabel.setPrefSize(80, 30);
            statusLabel.setAlignment(Pos.CENTER);
            statusLabel.setStyle("-fx-background-color: " +
                    (book.getStatus().equalsIgnoreCase("Pending") ? "#ffcc00" : "#62f270") +
                    "; -fx-background-radius: 15px;");
            statusLabel.setFont(new Font("System Bold", 13));
            pane.getChildren().add(statusLabel);
        }
        // Thêm đường kẻ ngang
        Line line = new Line(0, 65, 795, 65);

        // Thêm các thành phần vào Pane
        pane.getChildren().addAll(bookTitleLabel, authorTileLabel, issueDateLabel, returnDateLabel, line);

        return pane;
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
