package src.librarysystem;

/**
 * Lớp này đại diện cho một bình luận về một cuốn sách. Một bình luận bao gồm tiêu đề sách, tên
 * người dùng, thời gian bình luận, nội dung bình luận và đánh giá.
 */
public class Comment {

  private String bookTitle;
  private String username;
  private MyDateTime time;
  private String content;
  private int rate;

  /**
   * Khởi tạo một đối tượng Comment mà không có tham số. Phương thức khởi tạo mặc định.
   */
  public Comment() {
  }

  /**
   * Khởi tạo một đối tượng Comment với đầy đủ các tham số.
   *
   * @param bookTitle Tiêu đề của cuốn sách mà bình luận liên quan.
   * @param username  Tên người dùng đã đăng bình luận.
   * @param time      Thời gian đăng bình luận.
   * @param content   Nội dung của bình luận.
   * @param rate      Đánh giá của người dùng về cuốn sách (thường là một số từ 1 đến 5).
   */
  public Comment(String bookTitle, String username, MyDateTime time, String content, int rate) {
    this.bookTitle = bookTitle;
    this.username = username;
    this.time = time;
    this.content = content;
    this.rate = rate;
  }

  /**
   * Lấy tiêu đề của cuốn sách mà bình luận liên quan.
   *
   * @return Tiêu đề của cuốn sách.
   */
  public String getBookTitle() {
    return bookTitle;
  }

  /**
   * Thiết lập tiêu đề của cuốn sách mà bình luận liên quan.
   *
   * @param bookTitle Tiêu đề của cuốn sách.
   */
  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  /**
   * Lấy tên người dùng đã đăng bình luận.
   *
   * @return Tên người dùng.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Thiết lập tên người dùng đã đăng bình luận.
   *
   * @param username Tên người dùng.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Lấy thời gian mà bình luận được đăng.
   *
   * @return Thời gian của bình luận.
   */
  public MyDateTime getTime() {
    return time;
  }

  /**
   * Thiết lập thời gian mà bình luận được đăng.
   *
   * @param time Thời gian của bình luận.
   */
  public void setTime(MyDateTime time) {
    this.time = time;
  }

  /**
   * Lấy nội dung của bình luận.
   *
   * @return Nội dung bình luận.
   */
  public String getContent() {
    return content;
  }

  /**
   * Thiết lập nội dung của bình luận.
   *
   * @param content Nội dung bình luận.
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Lấy đánh giá của người dùng về cuốn sách.
   *
   * @return Đánh giá của người dùng (thường là một số nguyên từ 1 đến 5).
   */
  public int getRate() {
    return rate;
  }

  /**
   * Thiết lập đánh giá của người dùng về cuốn sách.
   *
   * @param rate Đánh giá của người dùng (thường là một số nguyên từ 1 đến 5).
   */
  public void setRate(int rate) {
    this.rate = rate;
  }

  /**
   * Trả về chuỗi biểu diễn đối tượng Comment dưới dạng một chuỗi.
   *
   * @return Chuỗi đại diện cho đối tượng Comment.
   */
  @Override
  public String toString() {
    return "Comment{" +
        " bookTitle='" + bookTitle + '\'' +
        ", username='" + username + '\'' +
        ", time=" + time +
        ", content='" + content + '\'' + ", rate= " + rate + '\'' +
        '}';
  }
}