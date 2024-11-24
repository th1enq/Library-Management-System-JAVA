package src.librarysystem;

public class BookIssue {

  private int userId;
  private String username;
  private String bookTitle;
  private String bookAuthor;
  private MyDateTime issueDate;
  private MyDateTime returnDate;
  private String status;

  public static final String STATUS_PROCESSING = "Processing";
  public static final String STATUS_DELAY = "Delay";
  public static final String STATUS_RETURNED = "Returned";

  public BookIssue(int userId, String username, String bookTitle, String bookAuthor, MyDateTime issueDate, MyDateTime returnDate, String status) {
    this.userId = userId;
    this.username = username;
    this.bookTitle = bookTitle;
    this.bookAuthor = bookAuthor;
    setIssueDate(issueDate);
    setReturnDate(returnDate);
    setStatus(status);
  }

  // Constructor mặc định
  public BookIssue() {
    this.status = STATUS_PROCESSING;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {

    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public String getBookAuthor() {
    return bookAuthor;
  }

  public void setBookAuthor(String bookAuthor) {
    this.bookAuthor = bookAuthor;
  }

  public MyDateTime getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(MyDateTime issueDate) {
    this.issueDate = issueDate;
  }

  public MyDateTime getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(MyDateTime returnDate) {
    this.returnDate = returnDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

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
