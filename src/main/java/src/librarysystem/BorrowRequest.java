package src.librarysystem;

public class BorrowRequest {
  private int  id;
  private int userId;
  private String bookName;
  private String borrowDate;
  private String returnDate;
  private int accepted;

  public BorrowRequest() {
  }

  public BorrowRequest(int id, int userId, String bookName, String borrowDate, String returnDate, int accepted) {
    this.id = id;
    this.userId = userId;
    this.bookName = bookName;
    this.borrowDate = borrowDate;
    this.returnDate = returnDate;
    this.accepted = accepted;
  }


  // Getter và Setter cho từng thuộc tính
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public String getBorrowDate() {
    return borrowDate;
  }

  public void setBorrowDate(String borrowDate) {
    this.borrowDate = borrowDate;
  }

  public String getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(String returnDate) {
    this.returnDate = returnDate;
  }

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
        ", userId=" + userId +
        ", bookName='" + bookName + '\'' +
        ", borrowDate='" + borrowDate + '\'' +
        ", returnDate='" + returnDate + '\'' +
        ", accepted=" + accepted +
        '}';
  }
}
