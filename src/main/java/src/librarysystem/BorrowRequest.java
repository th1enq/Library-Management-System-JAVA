package src.librarysystem;

/**
 * Lớp này đại diện cho một yêu cầu mượn sách trong hệ thống. Nó kế thừa từ lớp {@link BookIssue} và
 * bổ sung thêm các thông tin về ID của yêu cầu và trạng thái chấp nhận.
 *
 * @see BookIssue
 */
public class BorrowRequest extends BookIssue {

  /**
   * ID của yêu cầu mượn sách.
   */
  private int id;

  /**
   * Trạng thái chấp nhận yêu cầu mượn sách (1: chấp nhận, 0: không chấp nhận).
   */
  private int accepted;

  /**
   * Khởi tạo một đối tượng {@link BorrowRequest} với các giá trị mặc định. Phương thức này gọi đến
   * constructor của lớp {@link BookIssue} với các giá trị mặc định.
   */
  public BorrowRequest() {
    super();
  }

  /**
   * Khởi tạo một đối tượng {@link BorrowRequest} với các giá trị cụ thể. Phương thức này gọi đến
   * constructor của lớp {@link BookIssue} với các thông tin cơ bản như userId, username, bookTitle,
   * bookAuthor, issueDate, returnDate.
   *
   * @param id         ID của yêu cầu mượn sách.
   * @param userId     ID của người dùng yêu cầu.
   * @param username   Tên người dùng.
   * @param bookTitle  Tiêu đề của cuốn sách.
   * @param bookAuthor Tác giả của cuốn sách.
   * @param issueDate  Ngày mượn sách.
   * @param returnDate Ngày trả sách.
   * @param accepted   Trạng thái chấp nhận yêu cầu mượn sách (1: chấp nhận, 0: không chấp nhận).
   */
  public BorrowRequest(int id, int userId, String username, String bookTitle, String bookAuthor,
      MyDateTime issueDate, MyDateTime returnDate, int accepted) {
    super(userId, username, bookTitle, bookAuthor, issueDate, returnDate, STATUS_PENDING);
    this.id = id;
    this.accepted = accepted;
  }

  /**
   * Lấy giá trị ID của yêu cầu mượn sách.
   *
   * @return ID của yêu cầu mượn sách.
   */
  public int getId() {
    return id;
  }

  /**
   * Thiết lập giá trị ID cho yêu cầu mượn sách.
   *
   * @param id ID của yêu cầu mượn sách.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Lấy giá trị trạng thái chấp nhận yêu cầu mượn sách.
   *
   * @return Trạng thái chấp nhận yêu cầu mượn sách (1: chấp nhận, 0: không chấp nhận).
   */
  public int isAccepted() {
    return accepted;
  }

  /**
   * Thiết lập giá trị trạng thái chấp nhận yêu cầu mượn sách.
   *
   * @param accepted Trạng thái chấp nhận yêu cầu mượn sách (1: chấp nhận, 0: không chấp nhận).
   */
  public void setAccepted(int accepted) {
    this.accepted = accepted;
  }

  /**
   * Trả về chuỗi mô tả thông tin của yêu cầu mượn sách. Mô tả chi tiết bao gồm ID của yêu cầu, ID
   * người dùng, tên người dùng, tiêu đề sách, tác giả, ngày mượn, ngày trả, trạng thái và trạng
   * thái chấp nhận.
   *
   * @return Chuỗi mô tả chi tiết về yêu cầu mượn sách.
   */
  @Override
  public String toString() {
    return "BorrowRequest{" +
        "id=" + id +
        ", userId=" + getUserId() +
        ", username='" + getUsername() + '\'' +
        ", bookTitle='" + getBookTitle() + '\'' +
        ", bookAuthor='" + getBookAuthor() + '\'' +
        ", issueDate=" + getIssueDate() +
        ", returnDate=" + (getReturnDate() != null ? getReturnDate() : "Chưa thiết lập") +
        ", status='" + getStatus() + '\'' +
        ", accepted=" + accepted +
        '}';
  }
}