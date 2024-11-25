package src.librarysystem;

public class Comment {

  private String bookTitle;
  private String username;
  private MyDateTime time;
  private String content;
  private int rate;

  public Comment() {
  }

  public Comment(String bookTitle, String username, MyDateTime time, String content, int rate) {
    this.bookTitle = bookTitle;
    this.username = username;
    this.time = time;
    this.content = content;
    this.rate = rate;
  }


  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public MyDateTime getTime() {
    return time;
  }

  public void setTime(MyDateTime time) {
    this.time = time;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public int getRate() {
    return rate;
  }

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