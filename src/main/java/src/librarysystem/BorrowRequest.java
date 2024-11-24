package src.librarysystem;

public class BorrowRequest extends BookIssue {
  private int id;
  private int accepted;
  public BorrowRequest() {
    super(); 
  }

  public BorrowRequest(int id, int userId, String username, String bookTitle, String bookAuthor, MyDateTime issueDate, MyDateTime returnDate, int accepted) {
    super(userId, username, bookTitle, bookAuthor, issueDate, returnDate,STATUS_PROCESSING );
    this.id = id;
    this.accepted = accepted;
  }

  // Getter và Setter cho `id`
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // Getter và Setter cho `accepted`
  public int isAccepted() {
    return accepted;
  }

  public void setAccepted(int accepted) {
    this.accepted = accepted;
  }

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
