package src.librarysystem;

import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;

public class MainGUI implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public FontAwesomeIcon homeIcon;
    @FXML
    public FontAwesomeIcon bookIcon;
    public FontAwesomeIcon statisticsIcon;
    public FontAwesomeIcon userIcon;
    public FontAwesomeIcon settingIcon;
    public Button statisticsButton;
    public Button userButton;
    public Button settingButton;
    public FontAwesomeIcon logOutIcon;
    @FXML
    public TextField bookQuery;
    @FXML
    private Button logOutButton;

    @FXML
    private Button minimizeButton;

    @FXML Button bookViewButton;

    @FXML
    private void minimze() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    private void close() {
        System.exit(0);
    }

    private double x;
    private double y;
    private int currentStage = 0;
    private boolean buttonShowing = true;

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logOutButton) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();


                Stage stage = new Stage();

                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();

                logOutButton.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private VBox mainVbox;

    private Parent fxml;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
            fxml = loader.load();
            DashBoardController dashBoardController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

//            dashBoardController.getSeeAllBook().setOnAction(event -> {
//                bookView();
//            });
//
//            dashBoardController.getAddBookButton().setOnAction(event -> {
//                addBook();
//            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
        searchQuery();
    }

    private Label loadingLabel;
    private String query;
    private ArrayList<Book> result;

    private void turnOnLoading() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadingIndicator.fxml"));
            fxml = loader.load();

            LoadingController loadingIndicator = loader.getController();
            loadingLabel = loadingIndicator.getLoadingLabel();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateProgressLabel(Task<ArrayList<Book>> task) {
        task.progressProperty().addListener((obs, oldValue, newValue) -> {
            int percent = (int) (newValue.doubleValue() * 100);
            loadingLabel.setText(percent + "%");
        });
    }


    private void searchQuery() {
        bookQuery.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                currentStage = 1;
                query = bookQuery.getText();
                turnOnLoading(); // Hiển thị màn hình loading ngay lập tức

                // Sử dụng Task để tìm kiếm
                Task<ArrayList<Book>> searchTask = new Task<>() {
                    @Override
                    protected ArrayList<Book> call() {

                        // Giả định là BookServices.searchBooks có thể thực hiện một truy vấn lớn
                        try {
                            // Cập nhật trạng thái loading
                            updateProgress(0, 100); // Đặt tiến trình về 0 trước khi bắt đầu

                            // Thực hiện tìm kiếm
                            result = BookServices.searchBooks(query);

                            // Nếu cần mô phỏng tiến trình tìm kiếm, có thể sử dụng loop này
                            for (int i = 1; i <= 100; i++) {
                                Thread.sleep(10); // Thời gian giả lập
                                updateProgress(i, 100); // Cập nhật tiến độ từ 0 đến 100
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return result;
                    }


                    @Override
                    protected void succeeded() {
                        System.out.println("done find book\n");
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchBook.fxml"));
                            fxml = loader.load();

                            SearchBookController searchBookController = loader.getController();
                            searchBookController.show(result); // Display results in the search view

                            searchBookController.getSeeDetailBook().setOnAction(e -> {
                                returnDetailBook(searchBookController.getCurrentBook());
                            });

                            mainVbox.getChildren().clear();
                            mainVbox.getChildren().setAll(fxml); // Load SearchBook.fxml into mainVbox
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }


                    @Override
                    protected void failed() {
                        // Xử lý khi có lỗi
                        Throwable throwable = getException();
                        throwable.printStackTrace();
                    }
                };
                updateProgressLabel(searchTask);

                new Thread(searchTask).start(); // Chạy Task trong một Thread mới
            }
        });
        update();
    }

    private void returnSearchBook() {
        currentStage = 1;
        query = bookQuery.getText();
        turnOnLoading(); // Hiển thị màn hình loading ngay lập tức

        // Sử dụng Task để tìm kiếm
        Task<ArrayList<Book>> searchTask = new Task<>() {
            @Override
            protected ArrayList<Book> call() {

                // Giả định là BookServices.searchBooks có thể thực hiện một truy vấn lớn
                try {
                    // Cập nhật trạng thái loading
                    updateProgress(0, 100); // Đặt tiến trình về 0 trước khi bắt đầu

                    // Thực hiện tìm kiếm
                    result = BookServices.searchBooks(query);

                    // Nếu cần mô phỏng tiến trình tìm kiếm, có thể sử dụng loop này
                    for (int i = 1; i <= 100; i++) {
                        Thread.sleep(10); // Thời gian giả lập
                        updateProgress(i, 100); // Cập nhật tiến độ từ 0 đến 100
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }


            @Override
            protected void succeeded() {
                System.out.println("done find book\n");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchBook.fxml"));
                    fxml = loader.load();

                    SearchBookController searchBookController = loader.getController();
                    searchBookController.show(result); // Display results in the search view

                    searchBookController.getSeeDetailBook().setOnAction(e -> {
                        returnDetailBook(searchBookController.getCurrentBook());
                    });

                    mainVbox.getChildren().clear();
                    mainVbox.getChildren().setAll(fxml); // Load SearchBook.fxml into mainVbox
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            @Override
            protected void failed() {
                // Xử lý khi có lỗi
                Throwable throwable = getException();
                throwable.printStackTrace();
            }
        };
        updateProgressLabel(searchTask);

        new Thread(searchTask).start(); // Chạy Task trong một Thread mới
    }

    private void returnDetailBook(Book currentBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookViewDetail.fxml"));
            fxml = loader.load();

            BookViewDetailController bookViewDetailController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            bookViewDetailController.initialize(currentBook);
            bookViewDetailController.getReturnSearchBook().setOnAction(e -> {
                returnSearchBook();
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update() {
        reset();
        System.out.println(currentStage);
        switch (currentStage) {
            case 0:
                homeIcon.getStyleClass().add("icon-active-color");
                homeButton.getStyleClass().add("menu-btn-active");
                break;
            case 1:
                bookIcon.getStyleClass().add("icon-active-color");
                bookViewButton.getStyleClass().add("menu-btn-active");
                break;
            case 2 :
                userIcon.getStyleClass().add("icon-active-color");
                userButton.getStyleClass().add("menu-btn-active");
                break;
        }
    }

    private void reset() {
        // Clear the styles for all buttons and icons before applying new styles
        homeIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        bookIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        userIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        statisticsIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        settingIcon.getStyleClass().removeAll("icon-active-color", "icon-color");
        logOutIcon.getStyleClass().removeAll("icon-active-color", "icon-color");

        homeButton.getStyleClass().removeAll("menu-btn-active", "parent");
        bookViewButton.getStyleClass().removeAll("menu-btn-active", "parent");
        userButton.getStyleClass().removeAll("menu-btn-active", "parent");
        statisticsButton.getStyleClass().removeAll("menu-btn-active", "parent");
        settingButton.getStyleClass().removeAll("menu-btn-active", "parent");
        logOutButton.getStyleClass().removeAll("menu-btn-active", "parent");

        homeIcon.getStyleClass().add("icon-color");
        bookIcon.getStyleClass().add("icon-color");
        userIcon.getStyleClass().add("icon-color");
        statisticsIcon.getStyleClass().add("icon-color");
        settingIcon.getStyleClass().add("icon-color");
        logOutIcon.getStyleClass().add("icon-color");


        homeButton.getStyleClass().add("parent");
        bookViewButton.getStyleClass().add("parent");
        userButton.getStyleClass().add("parent");
        statisticsButton.getStyleClass().add("parent");
        settingButton.getStyleClass().add("parent");
        logOutButton.getStyleClass().add("parent");
    }

    private void fadeAnimation() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), mainVbox);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void returnHome() {
        currentStage = 0;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
            fxml = loader.load();
            DashBoardController dashBoardController = loader.getController();

            mainVbox.getChildren().clear();
            mainVbox.getChildren().setAll(fxml);

            fadeAnimation();

//            dashBoardController.getSeeAllBook().setOnAction(event -> {
//                bookView();
//            });
//
//            dashBoardController.getAddBookButton().setOnAction(event -> {
//                addBook();
//            });

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void bookView() {
        currentStage = 1;
        try {
            fxml = FXMLLoader.load(getClass().getResource("BookView.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);

            fadeAnimation();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void addBook() {
        currentStage = 1;
        try {
            fxml = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);

            fadeAnimation();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    @FXML
    private void userView() {
        currentStage = 2;
        try {
            fxml = FXMLLoader.load(getClass().getResource("userView.fxml"));
            mainVbox.getChildren().removeAll();
            mainVbox.getChildren().setAll(fxml);

            fadeAnimation();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }

    // Helper method to create a fade transition for a button
    private FadeTransition createFadeTransition(Button button, double targetOpacity, double duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), button);
        fadeTransition.setToValue(targetOpacity);
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);
        fadeTransition.setOnFinished(event -> button.setVisible(buttonShowing));
        return fadeTransition;
    }

    @FXML
    public void returnSetting(ActionEvent actionEvent) {
    }
}