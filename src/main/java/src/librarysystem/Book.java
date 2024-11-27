package src.librarysystem;

import java.util.ArrayList;

/**
 * Lớp đại diện cho một cuốn sách trong hệ thống thư viện.
 */
public class Book {

  private String type = "ebooks";
  private String title;
  private String authors;
  private String publisher;
  private String publishedDate;
  private String thumbnail;
  private String ISBN;
  private String description;
  private String numPage;
  private String category;
  private String price;
  private String language;
  private String buyLink;
  private int avail;
  private int rating;
  private int numView;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(String publishedDate) {
    this.publishedDate = publishedDate;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getISBN() {
    return ISBN;
  }

  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNumPage() {
    return numPage;
  }

  public void setNumPage(String numPage) {
    this.numPage = numPage;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getBuyLink() {
    return buyLink;
  }

  public void setBuyLink(String buyLink) {
    this.buyLink = buyLink;
  }

  public int getAvail() {
    return avail;
  }

  public void setAvail(int avail) {
    this.avail = avail;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public void setNumView(int numView) {
    this.numView = numView;
  }

  public int getNumView() {
    return numView;
  }

  /**
   * Phương thức khởi tạo không tham số, tạo một đối tượng sách mặc định.
   */
  public Book() {
  }

  /**
   * Phương thức khởi tạo với các thông tin cơ bản của sách.
   *
   * @param title         Tên sách.
   * @param ISBN          Mã ISBN của sách.
   * @param authors       Tên tác giả.
   * @param publisher     Nhà xuất bản.
   * @param publishedDate Ngày xuất bản.
   * @param description   Mô tả sách.
   * @param thumbnail     Hình ảnh bìa sách.
   * @param numPage       Số trang sách.
   * @param category      Thể loại sách.
   * @param price         Giá sách.
   * @param language      Ngôn ngữ của sách.
   * @param buyLink       Liên kết mua sách.
   */
  public Book(String title, String ISBN, String authors, String publisher,
      String publishedDate, String description, String thumbnail,
      String numPage, String category, String price, String language,
      String buyLink) {
    this.title = title;
    this.ISBN = ISBN;
    this.authors = authors;
    this.publishedDate = publishedDate;
    this.thumbnail = thumbnail;
    this.publisher = publisher;
    this.description = description;
    this.numPage = numPage;
    this.category = category;
    this.price = price;
    this.language = language;
    this.buyLink = buyLink;
    this.avail = 1;
    this.rating = 0;
  }

  /**
   * Phương thức khởi tạo với đầy đủ thông tin của sách.
   *
   * @param title         Tên sách.
   * @param ISBN          Mã ISBN của sách.
   * @param authors       Tên tác giả.
   * @param publisher     Nhà xuất bản.
   * @param publishedDate Ngày xuất bản.
   * @param description   Mô tả sách.
   * @param thumbnail     Hình ảnh bìa sách.
   * @param numPage       Số trang sách.
   * @param category      Thể loại sách.
   * @param price         Giá sách.
   * @param language      Ngôn ngữ của sách.
   * @param buyLink       Liên kết mua sách.
   * @param avail         Số lượng sách có sẵn.
   * @param rating        Đánh giá sách (từ 0 đến 5).
   */
  public Book(String title, String ISBN, String authors, String publisher,
      String publishedDate, String description, String thumbnail,
      String numPage, String category, String price, String language,
      String buyLink, int avail, int rating) {
    this.title = title;
    this.ISBN = ISBN;
    this.authors = authors;
    this.publishedDate = publishedDate;
    this.thumbnail = thumbnail;
    this.publisher = publisher;
    this.description = description;
    this.numPage = numPage;
    this.category = category;
    this.price = price;
    this.language = language;
    this.buyLink = buyLink;
    this.avail = avail;
    this.rating = rating;
  }

  // Ví dụ cho các getter và setter:

  /**
   * Lấy tiêu đề của sách.
   *
   * @return Tiêu đề sách.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Đặt tiêu đề cho sách.
   *
   * @param title Tiêu đề cần đặt.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Lấy danh sách các bình luận về sách.
   *
   * @return Danh sách các bình luận.
   */
  public ArrayList<Comment> getCommentList() {
    return DBInfo.getCommentList(this);
  }

  /**
   * Trả về chuỗi biểu diễn thông tin sách.
   *
   * @return Chuỗi chứa thông tin của sách.
   */
  @Override
  public String toString() {
    return "Book ( " +
        "Type='" + type + '\'' +
        ", Title='" + title + '\'' +
        ", Authors='" + authors + '\'' +
        ", Publisher='" + publisher + '\'' +
        ", Published Date='" + publishedDate + '\'' +
        ", ISBN='" + ISBN + '\'' +
        ", Description='" + description + '\'' +
        ", Thumbnail='" + thumbnail + '\'' +
        ", Number of Pages='" + numPage + '\'' +
        ", Category='" + category + '\'' +
        ", Price='" + price + '\'' +
        ", Language='" + language + '\'' +
        ", Buy Link='" + buyLink + '\'' +
        ", Availability='" + avail + '\'' +
        ", Rating=" + rating +
        ") ";
  }
}