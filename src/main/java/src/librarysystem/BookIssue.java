package src.librarysystem;

/**
 * Lớp đại diện cho thông tin về việc mượn sách trong hệ thống thư viện.
 */
public class BookIssue {

    private int userId;
    private String username;
    private String bookTitle;
    private String bookAuthor;
    private MyDateTime issueDate;
    private MyDateTime returnDate;
    private String status;

    /**
     * Trạng thái "Đang chờ xử lý" của việc mượn sách.
     */
    public static final String STATUS_PENDING = "Pending";

    /**
     * Trạng thái "Trễ hạn" của việc mượn sách.
     */
    public static final String STATUS_DELAY = "Delay";

    /**
     * Trạng thái "Đã trả sách" của việc mượn sách.
     */
    public static final String STATUS_RETURNED = "Returned";

    /**
     * Phương thức khởi tạo đầy đủ thông tin của một lần mượn sách.
     *
     * @param userId     ID của người dùng.
     * @param username   Tên người dùng.
     * @param bookTitle  Tựa đề của sách.
     * @param bookAuthor Tác giả của sách.
     * @param issueDate  Ngày mượn sách.
     * @param returnDate Ngày trả sách (có thể null nếu chưa trả).
     * @param status     Trạng thái mượn sách (Pending, Delay, Returned).
     */
    public BookIssue(int userId, String username, String bookTitle, String bookAuthor,
                     MyDateTime issueDate, MyDateTime returnDate, String status) {
        this.userId = userId;
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        setIssueDate(issueDate);
        setReturnDate(returnDate);
        setStatus(status);
    }

    /**
     * Phương thức khởi tạo mặc định, với trạng thái ban đầu là "Pending".
     */
    public BookIssue() {
        this.status = STATUS_PENDING;
    }

    /**
     * Lấy ID của người dùng.
     *
     * @return ID của người dùng.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Đặt ID cho người dùng.
     *
     * @param userId ID cần đặt.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Lấy tên người dùng.
     *
     * @return Tên người dùng.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Đặt tên cho người dùng.
     *
     * @param username Tên người dùng cần đặt.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Lấy tựa đề của sách được mượn.
     *
     * @return Tựa đề sách.
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Đặt tựa đề cho sách được mượn.
     *
     * @param bookTitle Tựa đề cần đặt.
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /**
     * Lấy tên tác giả của sách được mượn.
     *
     * @return Tên tác giả của sách.
     */
    public String getBookAuthor() {
        return bookAuthor;
    }

    /**
     * Đặt tên tác giả cho sách được mượn.
     *
     * @param bookAuthor Tên tác giả cần đặt.
     */
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    /**
     * Lấy ngày mượn sách.
     *
     * @return Ngày mượn sách.
     */
    public MyDateTime getIssueDate() {
        return issueDate;
    }

    /**
     * Đặt ngày mượn sách.
     *
     * @param issueDate Ngày mượn cần đặt.
     */
    public void setIssueDate(MyDateTime issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Lấy ngày trả sách.
     *
     * @return Ngày trả sách hoặc null nếu chưa được thiết lập.
     */
    public MyDateTime getReturnDate() {
        return returnDate;
    }

    /**
     * Đặt ngày trả sách.
     *
     * @param returnDate Ngày trả sách cần đặt.
     */
    public void setReturnDate(MyDateTime returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Lấy trạng thái mượn sách.
     *
     * @return Trạng thái mượn sách (Pending, Delay, Returned).
     */
    public String getStatus() {
        return status;
    }

    /**
     * Đặt trạng thái mượn sách.
     *
     * @param status Trạng thái cần đặt (Pending, Delay, Returned).
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Hiển thị thông tin chi tiết về việc mượn sách.
     */
    public void displayIssueInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Book Title: " + bookTitle);
        System.out.println("Book Author: " + bookAuthor);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Return Date: " + (returnDate != null ? returnDate : "Chưa thiết lập"));
        System.out.println("Status: " + status);
    }
}