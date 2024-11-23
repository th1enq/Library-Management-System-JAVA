package src.librarysystem;


import com.beust.ah.A;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * kieu du lieu gom 4 String.
 */
class CustomData {

  private String first;

  private String second;

  private String third;

  private String fourth;

  public CustomData(String first, String second, String third, String fourth) {
    this.first = first;
    this.second = second;
    this.fourth = fourth;
    this.third = third;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getSecond() {
    return second;
  }

  public void setSecond(String second) {
    this.second = second;
  }

  public String getFourth() {
    return fourth;
  }

  public void setFourth(String fourth) {
    this.fourth = fourth;
  }

  public String getThird() {
    return third;
  }

  public void setthird(String third) {
    this.third = third;
  }

  public void print() {
    System.out.println(first + " " + second + " " + third + " " + fourth);
  }

}

class Pair<K, V> {

  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return
        key + " " + value;
  }
}

public class DBInfo {

  public static String curUsername = "";
  public static String curPass = "";
  public static int curId = 0;
  public static String userType;
  public static String curName = "";

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      System.out.println("Driver Loaded...");
      NotificationFromSystem();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  public static Connection conn() {
    Connection con = null;
    try {
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TESTT", "root", "");
      System.out.println("Ket noi thanh cong");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return con;
  }

  public static void NotificationFromSystem() {

    for (int id = 1; id <= DBInfo.getUserCount(); id++) {
      deleteNotificationsByUserId(id);
      String sqlUpcoming = "SELECT book_name, return_date FROM borrow_slip " +
          "WHERE return_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY) " +
          "AND user_id = ? ORDER BY return_date";

      String sqlOverdue = "SELECT book_name, return_date FROM borrow_slip WHERE return_date < NOW() AND user_id = ?";

      try (Connection conn = DBInfo.conn();
          PreparedStatement preparedStatement = conn.prepareStatement(sqlUpcoming);
          PreparedStatement statement2 = conn.prepareStatement(sqlOverdue)) {

        preparedStatement.setInt(1, id);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            String bookName = resultSet.getString("book_name");
            LocalDateTime returnDate = resultSet.getTimestamp("return_date").toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            long minutesBetween = ChronoUnit.MINUTES.between(now, returnDate);
            long hoursBetween = minutesBetween / 60;
            long daysBetween = hoursBetween / 24;
            hoursBetween %= 24;
            minutesBetween %= 60;
            String mess = "Còn " + daysBetween + " ngày " + hoursBetween + " giờ " + minutesBetween
                + " phút nữa là đến hạn trả cuốn: " + bookName;
            sendNotification(1000, id, mess);
          }
        }

        statement2.setInt(1, id);
        try (ResultSet resultSet2 = statement2.executeQuery()) {
          while (resultSet2.next()) {
            String bookName = resultSet2.getString("book_name");
            LocalDateTime returnDate = resultSet2.getTimestamp("return_date").toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            long minutesOverdue = ChronoUnit.MINUTES.between(returnDate, now);
            long hoursOverdue = minutesOverdue / 60;
            long daysOverdue = hoursOverdue / 24;
            hoursOverdue %= 24;
            minutesOverdue %= 60;
            String mess =
                "Đã quá hạn " + daysOverdue + " ngày " + hoursOverdue + " giờ " + minutesOverdue
                    + " phút để trả cuốn: " + bookName;
            sendNotification(1000, id, mess);

          }
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  public static User getUserById(int userId) {
    String sql = "SELECT * FROM registration WHERE id = ?";
    User user = null;
    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String userType = rs.getString("usertype");
        boolean isBanned = rs.getBoolean("is_banned");
        String avatarLink = rs.getString("avatar_link");
        String MSV = rs.getString("MSV");
        String university = rs.getString("University");
        String phone = rs.getString("Phone");
        String coverPhotoLink = rs.getString("Cover_photo_link");
        int reputation = rs.getInt("Reputation");
        user = new User(id, name, username, password, userType, isBanned, avatarLink, MSV,
            university, phone, coverPhotoLink, reputation);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }

  public static ArrayList<Notification> getNotificationsByUserId(int userId) {
    ArrayList<Notification> notifications = new ArrayList<>();
    String sql = "SELECT * FROM notifications WHERE sender_id = ? OR receiver_id = ?";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);
      stmt.setInt(2, userId);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        int senderId = rs.getInt("sender_id");
        int receiverId = rs.getInt("receiver_id");
        String message = rs.getString("message");
        Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
        LocalDateTime createdAtLocalDateTime = createdAtTimestamp.toLocalDateTime();
        MyDateTime createdAt = new MyDateTime(createdAtLocalDateTime);
        Notification notification = new Notification(id, senderId, receiverId, message, createdAt);
        notifications.add(notification);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return notifications;
  }

  public static void deleteNotificationsByUserId(int userId) {
    String sql = "DELETE FROM notifications WHERE sender_id = ? OR receiver_id = ?";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setInt(2, userId);
      int rowsAffected = stmt.executeUpdate();
      System.out.println("Deleted " + rowsAffected + " notifications for user ID: " + userId);

    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("Error while deleting notifications for user ID: " + userId);
    }
  }

  public static void sendNotification(int senderId, int receiverId, String message) {
    String sql = "INSERT INTO notifications (sender_id, receiver_id, message) VALUES (?, ?, ?)";
    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, senderId);
      stmt.setInt(2, receiverId);
      stmt.setString(3, message);
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Notification sent successfully.");
      } else {
        System.out.println("Failed to send notification.");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Vector<String> getValue(String name) {
    Vector<String> v = new Vector<>();

    return v;
  }

  public static Vector<Vector> outerVector;
  public static Vector colsName;

  public static void allBooks() throws SQLException {

  }

  public static Vector<Vector> outerVector1;
  public static Vector colsName1;


  public static Vector<Vector> outerVector2;
  public static Vector colsName2;


  public static String value;

  /**
   * check xem trong Database co quyen sach can tim ko?.
   *
   * @param itemName ten Quyen sach can tim
   * @return
   */
  public static boolean inDb(String itemName) {
    try {
      String query = "SELECT * FROM `book` WHERE title = ? and avail = ?";
      Connection con = DBInfo.conn();
      PreparedStatement preparedStatement = con.prepareStatement(query);
      preparedStatement.setString(1, itemName);
      preparedStatement.setString(2, "YES");
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement.close();
        con.close();
        return true;
      }
      preparedStatement.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void ban(User X){
    String username = X.getUsername();
    try {
      Connection con = DBInfo.conn();
      String sql = "UPDATE registration SET is_banned = ? WHERE username = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setInt(1, 1);
      preparedStatement.setString(2, username);
      int rowAffected = preparedStatement.executeUpdate();
      if (rowAffected > 0) {
        System.out.println("da ban ng dung o dong " + rowAffected);
      }
      preparedStatement.close();
      con.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public static void unBan(User X){
    String username = X.getUsername();
    try {
      Connection con = DBInfo.conn();
      String sql = "UPDATE registration SET is_banned = ? WHERE username = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setInt(1, 0);
      preparedStatement.setString(2, username);
      int rowAffected = preparedStatement.executeUpdate();
      if (rowAffected > 0) {
        System.out.println("da unBan ng dung o dong " + rowAffected);
      }
      preparedStatement.close();
      con.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public static void addSlip(String itemName, int id) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    try {
      con = DBInfo.conn();
      String sql = "INSERT INTO borrow_slip(user_id, book_name, borrow_date, return_date) VALUES (?, ?, ?, ?)";
      preparedStatement = con.prepareStatement(sql);

      LocalDateTime currentDateTime = LocalDateTime.now();
      LocalDateTime dateTimeAfter10Days = currentDateTime.plusDays(10);

      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, itemName);
      preparedStatement.setTimestamp(3, Timestamp.valueOf(currentDateTime));
      preparedStatement.setTimestamp(4, Timestamp.valueOf(dateTimeAfter10Days));
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Slip added successfully! Rows affected: " + rowsAffected);
      sendNotification(1000, id, "Đã mượn sách thành công");

    } catch (SQLException e) {
      System.out.println("Error adding slip");
      e.printStackTrace();
    } finally {
      // Ensure resources are closed
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * muon sach.
   *
   * @param itemName ten sach
   */
  public static void borrowBook(String itemName, int id) {
    try {
      if (!inDb(itemName)) {
        System.out.println("ko co cuon sach tren trong DB");
        sendNotification(1000, id, "Cuốn sách bạn muốn mươợn đã hết, vui lòng mượn cuốn khác");
        return;
      }
      Connection con = DBInfo.conn();
      String sql = "UPDATE book SET avail = ? WHERE title = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, "NO");
      preparedStatement.setString(2, itemName);
      int rowAffected = preparedStatement.executeUpdate();
      if (rowAffected > 0) {
        System.out.println("Thay doi trang thai dong" + rowAffected);
        // addSlip(itemName, id);
        sendNotification(1000, id, "Yêu cầu mượn sách đang chờ được admin xét duyệt");
        addBorrowRequest(itemName, id);
      }
      preparedStatement.close();
      con.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void addBorrowRequest(String itemName, int id) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    try {
      con = DBInfo.conn();
      String sql = "INSERT INTO borrow_request(user_id, book_name, borrow_date, return_date) VALUES (?, ?, ?, ?)";
      preparedStatement = con.prepareStatement(sql);

      LocalDateTime currentDateTime = LocalDateTime.now();
      LocalDateTime dateTimeAfter10Days = currentDateTime.plusDays(10);

      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, itemName);
      preparedStatement.setTimestamp(3, Timestamp.valueOf(currentDateTime));
      preparedStatement.setTimestamp(4, Timestamp.valueOf(dateTimeAfter10Days));
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Request added successfully! Rows affected: " + rowsAffected);

    } catch (SQLException e) {
      System.out.println("Error adding request");
      e.printStackTrace();
    } finally {
      // Ensure resources are closed
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static ArrayList<BorrowRequest> getBorrowRequest() {
    ArrayList<BorrowRequest> borrowRequestList = new ArrayList<>();
    String sql = "SELECT * FROM borrow_request WHERE accepted = 0";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        int userId = (rs.getInt("user_id"));
        String bookName = rs.getString("book_name");
        String borrowDate = rs.getString("borrow_date");
        String returnDate = rs.getString("return_date");

        BorrowRequest request = new BorrowRequest(id, userId, bookName, borrowDate, returnDate, 0);
        borrowRequestList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return borrowRequestList;
  }

  public static void acceptBorrowRequest(BorrowRequest request) {
    int id = request.getId();
    String sql = "UPDATE borrow_request SET accepted = 1 WHERE id = ?";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      int rowsUpdated = stmt.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("Yêu cầu mượn sách với ID = " + id + " đã được chấp nhận.");
        addSlip(request.getBookName(), request.getUserId());
      } else {
        System.out.println("Không tìm thấy yêu cầu mượn sách với ID = " + id);
      }
    } catch (SQLException e) {
      System.err.println("Lỗi khi cập nhật yêu cầu mượn sách: " + e.getMessage());
    }
  }

  /**
   * tra sach
   */
  public static void returnBook(String itemName, int id) {

    Connection con = DBInfo.conn();
    try {

      String sql = "DELETE FROM borrow_slip WHERE book_name = ? and user_id = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, itemName);
      preparedStatement.setInt(2, id);
      System.out.println(id);
      int rowsAffected = 0;
      rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("xoa khoi slip thành công!");
        preparedStatement.close();
        con.close();
        Connection con2 = DBInfo.conn();
        String sql2 = "UPDATE book SET avail = ? WHERE title = ?";
        PreparedStatement preparedStatement2 = con2.prepareStatement(sql2);
        preparedStatement2.setString(1, "YES");
        preparedStatement2.setString(2, itemName);
        int rowAffected2 = preparedStatement2.executeUpdate();
        if (rowAffected2 > 0) {
          System.out.println("Thay doi trang thai dong" + rowAffected2);
          sendNotification(1000, id, "Trả sách thành công");

        } else {
          System.out.println("Thay doi trang thai ko thanh cong");
        }
        preparedStatement.close();
        con.close();

      } else {
        System.out.println("Không tìm thấy người trong slip cuon: " + itemName);
      }

      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      EE.printStackTrace();
      System.out.println("Loi xoa user ");
    }
  }


  public static void editBook(Book a, String newTitle, String newAuthor, String newPublisher,
      String newThumbnail, String newDescription, String newPageNum, String newLanguage) {
    String oldTitle = a.getTitle();

    // Create a StringBuilder to dynamically build the SQL query
    StringBuilder sql = new StringBuilder("UPDATE book SET ");
    boolean isFirst = true;

    // Use a list to keep track of the parameters to be updated
    List<String> params = new ArrayList<>();

    // Check each field and only add it to the update query if it's not empty
    if (newTitle != null && !newTitle.isEmpty()) {
      sql.append("title = ?");
      params.add(newTitle);
      isFirst = false;
    }
    if (newAuthor != null && !newAuthor.isEmpty()) {
      if (!isFirst) {
        sql.append(", ");
      }
      sql.append("authors = ?");
      params.add(newAuthor);
      isFirst = false;
    }
    if (newPublisher != null && !newPublisher.isEmpty()) {
      if (!isFirst) {
        sql.append(", ");
      }
      sql.append("publisher = ?");
      params.add(newPublisher);
      isFirst = false;
    }
    if (newDescription != null && !newDescription.isEmpty()) {
      if (!isFirst) {
        sql.append(", ");
      }
      sql.append("description = ?");
      params.add(newDescription);
      isFirst = false;
    }
    if (newPageNum != null && !newPageNum.isEmpty()) {
      if (!isFirst) {
        sql.append(", ");
      }
      sql.append("numPage = ?");
      params.add(newPageNum);
      isFirst = false;
    }
    if (newLanguage != null && !newLanguage.isEmpty()) {
      if (!isFirst) {
        sql.append(", ");
      }
      sql.append("language = ?");
      params.add(newLanguage);
      isFirst = false;
    }
    if (newThumbnail != null && !newThumbnail.isEmpty()) {
      if (!isFirst) {
        sql.append(", ");
      }
      sql.append("thumbnail = ?");
      params.add(newThumbnail);
    }

    // If no parameters were set, there's nothing to update
    if (params.isEmpty()) {
      System.out.println("No updates were provided.");
      return;
    }

    // Add the WHERE clause to the query
    sql.append(" WHERE title = ?");

    try {
      Connection conn = DBInfo.conn();
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      for (int i = 0; i < params.size(); i++) {
        pstmt.setString(i + 1, params.get(i));
      }
      pstmt.setString(params.size() + 1, oldTitle);

      int rowsUpdated = pstmt.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("The book was updated successfully!");
      } else {
        System.out.println("No book with the given title found.");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static void addPublisher(String name) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO publisher(name) VALUE (?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, name);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("publisher added successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error adding publisher");
      EE.printStackTrace();
    }
  }

  public static void deletePublisher(String a) {
    try {
      Connection con = DBInfo.conn();
      String sql = "DELETE FROM publisher WHERE name = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("DEL publisher successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error deleting PUblisher");
      EE.printStackTrace();
    }
  }

  public static void deleteAuthor(String a) {
    try {
      Connection con = DBInfo.conn();
      String sql = "DELETE FROM author WHERE name = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("DEL author successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error deleting author");
      EE.printStackTrace();
    }
  }

  public static void addAuthor(String a) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO author(name) VALUE (?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("author added successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error adding author");
      EE.printStackTrace();
    }
  }

  public static void addCategory(String a) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO category(name) VALUE (?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Category added successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error adding Category");
      EE.printStackTrace();
    }
  }

  public static void deleteCategory(String a) {
    try {
      Connection con = DBInfo.conn();
      String sql = "DELETE FROM category WHERE name = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Category deleted successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error deleting Category");
      EE.printStackTrace();
    }
  }

  public static void addBook(Book A) {
    try {
      Connection con = DBInfo.conn();
      if (inDb(A.getTitle())) {
        System.out.println("da co quyen sach nay roi");
        con.close();
        return;
      }
      String sql =
          "INSERT INTO book (title, authors, publisher, publishedDate, thumbnail, ISBN, description, numPage, category, price, language, buyLink) "
              +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, A.getTitle());
      preparedStatement.setString(2, A.getAuthors());
      preparedStatement.setString(3, A.getPublisher());
      preparedStatement.setString(4, A.getPublishedDate());
      preparedStatement.setString(5, A.getThumbnail());
      preparedStatement.setString(6, A.getISBN());
      preparedStatement.setString(7, A.getDescription());
      preparedStatement.setString(8, A.getNumPage());
      preparedStatement.setString(9, A.getCategory());
      preparedStatement.setString(10, A.getPrice());
      preparedStatement.setString(11, A.getLanguage());
      preparedStatement.setString(12, A.getBuyLink());
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Book added successfully! Rows affected: " + rowsAffected);
      addCategory(A.getCategory());
      addPublisher(A.getPublisher());
      addAuthor(A.getAuthors());
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error adding book");
      EE.printStackTrace();
    }
  }

  /**
   * xóa sách.
   *
   * @param A tên sách
   */
  public static void deleteBook(Book A) {
    try {
      Connection con = DBInfo.conn();
      if (!inDb(A.getTitle())) {
        System.out.println("ko co quyen sach nay");
        con.close();
        return;
      }
      String sql = "DELETE FROM book WHERE title = ? LIMIT 1";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, A.getTitle());
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Book deleted successfully! Rows affected: " + rowsAffected);
      deleteCategory(A.getCategory());
      deletePublisher(A.getPublisher());
      deleteAuthor(A.getAuthors());
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error deleting book");
      EE.printStackTrace();
    }
  }

  public static int getUserCount() {
    int count = 0;
    String query = "SELECT MAX(id) FROM registration WHERE usertype = 'user' ";

    try (Connection connection = DBInfo.conn()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }

  public static int countUser() {
    int count = 0;
    String query = "SELECT COUNT(*) FROM registration WHERE usertype = 'user'";

    try (Connection connection = DBInfo.conn();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }

  public static int getBookCount() {
    int count = 0;
    String query = "SELECT COUNT(*) FROM book";

    try (Connection connection = DBInfo.conn()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }

  public static boolean isUsernameExists(String username) {
    boolean exists = false;
    String query = "SELECT 1 FROM registration WHERE username = ? LIMIT 1";
    try (Connection connection = DBInfo.conn()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();
      exists = resultSet.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return exists;
  }

  /**
   * dang ki nguoi moi.
   *
   * @param id       id
   * @param name     name
   * @param username us
   * @param password p
   * @param usertype admin hay ngdung binh thuong
   */
  public static void Register(int id, String name, String username, String password,
      String usertype) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO registration(id,name, username, password, usertype) VALUE (?,?,?,?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      int x = getUserCount() + 1;
      System.out.println(x);
      preparedStatement.setInt(1, x);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, username);
      preparedStatement.setString(4, password);
      preparedStatement.setString(5, usertype);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Register successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException ee) {
      ee.printStackTrace();
    }
  }
  public static void Register(int id, String name, String username, String password,
      String usertype, String MSV) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO registration(id,name, username, password, usertype, MSV) VALUE (?,?,?,?,?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      int x = getUserCount() + 1;
      System.out.println(x);
      preparedStatement.setInt(1, x);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, username);
      preparedStatement.setString(4, password);
      preparedStatement.setString(5, usertype);
      preparedStatement.setString(6, MSV);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Register successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException ee) {
      ee.printStackTrace();
    }
  }

  /**
   * kiem tra mat khau
   *
   * @param username us
   * @param password pas
   * @return true/false
   */
  public static boolean checkPass(String username, String password) {
    String sql = "SELECT usertype FROM registration WHERE username = ? AND password = ? AND is_banned = 0";

    try {
      Connection con = DBInfo.conn();
      if (con == null) {
        System.out.println("KO kết nối đc với db");
        return false;
      }
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String userType = resultSet.getString("usertype");
        System.out.println("Đúng! Loại người dùng: " + userType);
        return true;
      } else {
        System.out.println("Tên đăng nhập hoặc mật khẩu không đúng!");

      }
      preparedStatement.close();
      con.close();
      return false;
    } catch (SQLException A) {
      A.printStackTrace();
      return false;
    }
  }

  /**
   * hamf dang nhap.
   *
   * @param Username U
   * @param Password P
   */
  public static void login(String Username, String Password) {
    if (checkPass(Username, Password)) {
      curPass = Password;
      curUsername = Username;
      curId = findUserId(Username, Password);
      userType = findUserType(Username, Password);
      curName = findName(curId);
    }
  }

  public static String findName(int id) {
    String name = null;
    String query = "SELECT name FROM registration WHERE id = ?";
    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        name = rs.getString("name");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return name;
  }

  public static String findUserType(String username, String password) {
    try {
      Connection con = DBInfo.conn();
      String sql = "SELECT usertype FROM registration WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String type = resultSet.getString("usertype");
        System.out.println("type của người dùng là: " + type);
        return type;
      } else {
        System.out.println("Không tìm thấy người dùng với username và password đã cho.");

      }
      preparedStatement.close();
      con.close();
      return "KO TIM THAY";
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Loi ham tim type biet user va pass");
      return "KO TIM THAY";
    }
  }

  public static int findUserId(String username, String password) {
    try {
      Connection con = DBInfo.conn();
      String sql = "SELECT id FROM registration WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        int id = resultSet.getInt("id");
        System.out.println("ID của người dùng là: " + id);
        return id;
      } else {
        System.out.println("Không tìm thấy người dùng với username và password đã cho.");

      }
      preparedStatement.close();
      con.close();
      return -1;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Loi ham tim id biet user va pass");
      return -1;
    }
  }

  public static void updateUser(int id, String newName, String newUsername, String newPassword,
      String newAvatarLink) {
    Connection con = DBInfo.conn();
    if (con == null) {
      System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
      return;
    }

    PreparedStatement preparedStatement = null;
    try {
      StringBuilder sql = new StringBuilder("UPDATE registration SET ");
      boolean needComma = false;

      if (newName != null) {
        sql.append("name = ?");
        needComma = true;
      }
      if (newUsername != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("username = ?");
        needComma = true;
      }
      if (newPassword != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("password = ?");
        needComma = true;
      }
      if (newAvatarLink != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("avatar_link = ?");
      }
      sql.append(" WHERE id = ?");

      preparedStatement = con.prepareStatement(sql.toString());
      int paramIndex = 1;

      if (newName != null) {
        preparedStatement.setString(paramIndex++, newName);
      }
      if (newUsername != null) {
        preparedStatement.setString(paramIndex++, newUsername);
      }
      if (newPassword != null) {
        preparedStatement.setString(paramIndex++, newPassword);
      }
      if (newAvatarLink != null) {
        preparedStatement.setString(paramIndex++, newAvatarLink);
      }
      preparedStatement.setInt(paramIndex, id);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Cập nhật thành công!");
      } else {
        System.out.println("Không tìm thấy người dùng với id: " + id);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi sửa thông tin người dùng.");
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static Pair<Integer, Integer> getOverdueAndUpcoming(int id) {

    int upcoming = 0;
    int overdue = 0;

    String sqlUpcoming = "SELECT book_name, return_date FROM borrow_slip " +
        "WHERE return_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 11 DAY) " +
        "AND user_id = ? ORDER BY return_date";

    String sqlOverdue = "SELECT book_name, return_date FROM borrow_slip WHERE return_date < NOW() AND user_id = ?";

    try (Connection conn = DBInfo.conn();
        PreparedStatement preparedStatement = conn.prepareStatement(sqlUpcoming);
        PreparedStatement statement2 = conn.prepareStatement(sqlOverdue)) {

      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          upcoming++;
        }
      }

      statement2.setInt(1, id);
      try (ResultSet resultSet2 = statement2.executeQuery()) {
        while (resultSet2.next()) {
          overdue++;
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new Pair<Integer, Integer>(upcoming, overdue);
  }

  public static int countBorrowed() {
    int upcoming = 0;

    String sqlUpcoming = "SELECT book_name, return_date FROM borrow_slip " +
        "WHERE return_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 11 DAY)";

    try (Connection conn = DBInfo.conn();
        PreparedStatement preparedStatement = conn.prepareStatement(sqlUpcoming)) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        upcoming++;
      }

      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return upcoming;
  }

  public static int countOverdue() {
    int ret = 0;

    String sql = "SELECT book_name, return_date FROM borrow_slip WHERE return_date < NOW()";

    try (Connection conn = DBInfo.conn();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        ret++;
      }

      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return ret;
  }


  /**
   * chinh sua thong tin ng dung.
   *
   * @param id          id
   * @param newName     ten moi
   * @param newUsername username moi
   * @param newPassword pass moi
   */
  public static void updateUser(int id, String newName, String newUsername, String newPassword,
      String newAvatarLink,
      String newMSV, String newUniversity, String newPhone,
      String newCoverPhotoLink, Integer newReputation) {

    Connection con = DBInfo.conn();
    if (con == null) {
      System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
      return;
    }

    PreparedStatement preparedStatement = null;
    try {
      StringBuilder sql = new StringBuilder("UPDATE registration SET ");
      boolean needComma = false;

      // Cập nhật các trường trong bảng nếu giá trị không phải null
      if (newName != null) {
        sql.append("name = ?");
        needComma = true;
      }
      if (newUsername != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("username = ?");
        needComma = true;
      }
      if (newPassword != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("password = ?");
        needComma = true;
      }
      if (newAvatarLink != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("avatar_link = ?");
        needComma = true;
      }
      if (newMSV != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("MSV = ?");
        needComma = true;
      }
      if (newUniversity != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("University = ?");
        needComma = true;
      }
      if (newPhone != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("Phone = ?");
        needComma = true;
      }
      if (newCoverPhotoLink != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("Cover_photo_link = ?");
        needComma = true;
      }
      if (newReputation != null) {
        if (needComma) {
          sql.append(", ");
        }
        sql.append("Reputation = ?");
      }

      sql.append(" WHERE id = ?");

      preparedStatement = con.prepareStatement(sql.toString());
      int paramIndex = 1;

      // Thiết lập giá trị cho các tham số
      if (newName != null) {
        preparedStatement.setString(paramIndex++, newName);
      }
      if (newUsername != null) {
        preparedStatement.setString(paramIndex++, newUsername);
      }
      if (newPassword != null) {
        preparedStatement.setString(paramIndex++, newPassword);
      }
      if (newAvatarLink != null) {
        preparedStatement.setString(paramIndex++, newAvatarLink);
      }
      if (newMSV != null) {
        preparedStatement.setString(paramIndex++, newMSV);
      }
      if (newUniversity != null) {
        preparedStatement.setString(paramIndex++, newUniversity);
      }
      if (newPhone != null) {
        preparedStatement.setString(paramIndex++, newPhone);
      }
      if (newCoverPhotoLink != null) {
        preparedStatement.setString(paramIndex++, newCoverPhotoLink);
      }
      if (newReputation != null) {
        preparedStatement.setInt(paramIndex++, newReputation);
      }

      preparedStatement.setInt(paramIndex, id);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Cập nhật thành công!");
      } else {
        System.out.println("Không tìm thấy người dùng với id: " + id);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi sửa thông tin người dùng.");
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * chinh sua email ng dung.
   *
   * @param id       id
   * @param newEmail E
   */
  public static void updateEmail(int id, String newEmail) {
    Connection con = DBInfo.conn();
    try {

      String sql = "UPDATE registration SET username = ? WHERE id = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);

      preparedStatement.setString(1, newEmail);
      preparedStatement.setInt(2, id);
      int rowsAffected = 0;
      System.out.println(rowsAffected);
      rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("Cập nhật thành công!");
      } else {
        System.out.println("Không tìm thấy người dùng với id: " + id);
      }
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      EE.printStackTrace();
      System.out.println("Loi sua email");
    }
  }

  public static void DeleteUser(String name) {
    Connection con = DBInfo.conn();
    try {

      String sql = "DELETE FROM registration WHERE username = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);

      preparedStatement.setString(1, name);

      int rowsAffected = 0;
      rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("Cập nhật thành công!");
      } else {
        System.out.println("Không tìm thấy người dùng với username: " + name);
      }
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      EE.printStackTrace();
      System.out.println("Loi xoa user ");
    }
  }

  public static void rateBook(String itemName, int score) {
    try {
      Connection con = DBInfo.conn();
      String sql = "SELECT rating FROM book WHERE title = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, itemName);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {

        int rating = resultSet.getInt("rating");
        System.out.println("rating hien tai cua cuon sach la " + rating);
        Connection con2 = DBInfo.conn();

        String sql2 = "UPDATE book SET rating = ? WHERE title = ?";
        PreparedStatement preparedStatement2 = con2.prepareStatement(sql2);
        int newrating = (rating + score) / 2;
        preparedStatement2.setInt(1, newrating);
        preparedStatement2.setString(2, itemName);

        //System.out.println(newrating);
        int rowsAffected2 = 0;
        rowsAffected2 = preparedStatement2.executeUpdate();
        if (rowsAffected2 > 0) {
          System.out.println("Cập nhật thành công rating!");
        } else {
          System.out.println("Cập nhật ko thành công rating!");
        }
        preparedStatement2.close();
        con2.close();
        preparedStatement.close();
        con.close();
      } else {
        System.out.println("ko tim thay sach de rating");

      }
      preparedStatement.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Loi ham rateBook");
    }
  }

  /**
   * lấy ra danh sách các cuốn sách trong database.
   *
   * @return arraylist gồm 4 thông số id, tên, tác giả, avail.
   */
  public static ArrayList<User> getUserList() {
    Connection con = DBInfo.conn();
    ArrayList<User> ret = new ArrayList<>();
    String query = "SELECT * FROM registration WHERE usertype = 'user'";

    try {
      PreparedStatement ps = con.prepareStatement(query);
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        User user = new User(
            res.getInt("id"),
            res.getString("name"),
            res.getString("username"),
            res.getString("password"),
            res.getString("usertype"),
            res.getBoolean("is_banned"),
            res.getString("avatar_link")
        );
        ret.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ret;
  }

  public static Book getBook(String name) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE title = ?";
      preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, name);
      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        Book book = new Book();

        book.setTitle(resultSet.getString("title"));
        book.setAuthors(resultSet.getString("authors"));
        book.setPublisher(resultSet.getString("publisher"));

        LocalDate publishedDate = resultSet.getDate("publishedDate").toLocalDate();
        book.setPublishedDate(publishedDate.toString());

        book.setThumbnail(resultSet.getString("thumbnail"));
        book.setISBN(resultSet.getString("ISBN"));
        book.setDescription(resultSet.getString("description"));
        book.setNumPage(resultSet.getString("numPage"));
        book.setCategory(resultSet.getString("category"));
        book.setPrice(resultSet.getString("price"));
        book.setLanguage(resultSet.getString("language"));
        book.setBuyLink(resultSet.getString("buyLink"));
        book.setAvail(resultSet.getString("avail"));
        book.setRating(resultSet.getInt("rating"));

        return book;
      } else {
        System.out.println("Không tìm thấy sách có tên: " + name);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi lấy thông tin sách");
    } finally {
      // Đóng các tài nguyên khi hoàn thành
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static ArrayList<Pair<Book, MyDateTime>> getBorrowedBookList(int id) {
    ArrayList<Pair<Book, MyDateTime>> ret = new ArrayList<>();
    String query = "SELECT * FROM borrow_slip WHERE user_id = ?";

    try (Connection con = DBInfo.conn();
        PreparedStatement ps = con.prepareStatement(query)) {

      ps.setInt(1, id);
      try (ResultSet res = ps.executeQuery()) {
        while (res.next()) {
          String bookName = res.getString("book_name");
          LocalDateTime tmp = res.getTimestamp("borrow_date").toLocalDateTime();
          MyDateTime borrowDate = new MyDateTime(tmp);
          Book book = getBook(bookName);
          ret.add(new Pair<>(book, borrowDate));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ret;
  }

  /**
   * laay ra những giá trị phân biệt của 1 cột của 1 bảng trong database.
   *
   * @param columnName teen cột
   * @param tableName  tên bảng
   * @return arraylist chứa các phần tử thỏa mãn
   */
  public static ArrayList<String> getDistinctValues(String columnName, String tableName) {
    ArrayList<String> distinctValues = new ArrayList<>();
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      con = DBInfo.conn();
      String sql = "SELECT DISTINCT " + columnName + " FROM " + tableName;
      preparedStatement = con.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        distinctValues.add(resultSet.getString(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return distinctValues;
  }

  /**
   * lay ra cac cuon sach theo yeu cau cho trc.
   *
   * @param author    ten tac gia
   * @param category  the loai
   * @param publisher NXB
   * @return array list chua cac cuon sach thoa man
   */
  public static ArrayList<Book> getBookList(String author, String category, String publisher) {
    ArrayList<Book> bookList = new ArrayList<>();
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      con = DBInfo.conn();
      final String sql = getString(author, category, publisher);

      //  System.out.println(sql);
      preparedStatement = con.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String title = resultSet.getString("title");
        String ISBN = resultSet.getString("ISBN");
        String Author = resultSet.getString("authors");
        String Publisher = resultSet.getString("publisher");
        String publishedDate = resultSet.getString("publishedDate");
        String description = resultSet.getString("description");
        String thumbnail = resultSet.getString("thumbnail");
        String numPage = resultSet.getString("numPage");
        String Category = resultSet.getString("category");
        String price = resultSet.getString("price");
        String language = resultSet.getString("language");
        String buyLink = resultSet.getString("buyLink");
        String avail = resultSet.getString("avail");
        int rating = resultSet.getInt("rating");
        Book book = new Book(title, ISBN, Author, Publisher, publishedDate,
            description, thumbnail, numPage, Category, price,
            language, buyLink, avail, rating);
        bookList.add(book);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return bookList;
  }

  public static String getString(String author, String category, String publisher) {
    int prev = 0;
    String sql = "SELECT title, ISBN, authors, publisher, publishedDate, description,thumbnail, numPage, category, price, language, buyLink,avail,rating FROM book";
    if (author != null && !author.equals("ALL")) {
      sql += (" WHERE author = " + "'" + author + "'");
      prev++;
    }
    if (category != null && !category.equals("ALL")) {
      if (prev == 0) {
        sql += " WHERE ";
      } else {
        sql += " and ";
      }
      sql += ("category = " + "'" + category + "'");
      prev++;
    }
    if (publisher != null && !publisher.equals("ALL")) {
      if (prev == 0) {
        sql += " WHERE ";
      } else {
        sql += " and ";
      }
      sql += ("publisher = " + "'" + publisher + "'");
      prev++;
    }
    return sql;
  }

  public static void addComment(String book_title, String content) {
    try {
      Connection conn = DBInfo.conn();
      String sql = "INSERT INTO comment (book_title, username, time, content) VALUES (?, ?, ?,?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, book_title);
      stmt.setString(2, curUsername);
      java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());
      stmt.setDate(3, currentDate);
      stmt.setString(4, content);
      int rowsInserted = stmt.executeUpdate();
      if (rowsInserted > 0) {
        System.out.println("Thêm bình luận thành công!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<CustomData> getCommentList(String book_title) {
    ArrayList<CustomData> ret = new ArrayList<>();
    try {
      Connection conn = DBInfo.conn();
      // Sử dụng câu SQL với PreparedStatement
      String sql = "SELECT * from comment WHERE book_title = ?";
      PreparedStatement statement = conn.prepareStatement(sql);

      // Sử dụng phương thức setString để thiết lập giá trị cho tham số
      statement.setString(1, book_title);

      // Thực hiện câu lệnh truy vấn
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        ret.add(new CustomData(
            resultSet.getString("username"),
            resultSet.getDate("time").toString(),
            resultSet.getString("content"),
            "dummy"
        ));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  public static User getUser(String username) {
    User user = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      conn = DBInfo.conn();
      String sql = "SELECT * FROM registration WHERE username = ?";
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setUserType(rs.getString("usertype"));
        user.setBanned(rs.getBoolean("is_banned"));
        user.setAvatarLink(rs.getString("avatar_link"));
        user.setMSV(rs.getString("MSV"));
        user.setCoverPhotoLink(rs.getString("Cover_photo_link"));
        user.setPhone(rs.getString("Phone"));
        user.setUniversity(rs.getString("University"));
        user.setReputation(rs.getInt("Reputation"));


      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }

  public static boolean checkUnique(String X) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    boolean isUnique = true;
    try {
      conn = DBInfo.conn();
      String sql = "SELECT COUNT(*) FROM registration WHERE username = ?";
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, X);
      rs = stmt.executeQuery();
      if (rs.next()) {
        int count = rs.getInt(1);
        if (count > 0) {
          isUnique = false;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return isUnique;
  }

  public static void main(String[] args) {

    User X = getUser("nguyenvana");
    User Y = getUser("levanc");

    Y.unBan(X);
  }
}
