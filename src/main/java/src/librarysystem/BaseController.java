package src.librarysystem;

public class BaseController {
    private MainGUI mainGUIController;

    public void setMainGUIController(MainGUI mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    protected void sendNotification(int senderId, int receiverId, String content) {
        mainGUIController.sendNotification(senderId, receiverId, content);
    }

    protected void returnDetailBook(Book currentBook, boolean apiMode) {
        mainGUIController.returnDetailBook(currentBook, apiMode);
    }

    protected void updateNotifications() {
        mainGUIController.updateNotifications();
    }
}

