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

public class DBInfo {

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
      //  System.out.println("Ket noi thanh cong");
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
    String sql = "SELECT * FROM notifications WHERE receiver_id = ?";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, userId);

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

  /**
   * check xem trong Database co quyen sach can tim ko?.
   *
   * @param itemName ten Quyen sach can tim
   * @return
   */
  public static boolean inDb(String itemName) {
    try {
      String query = "SELECT * FROM `book` WHERE title = ? and avail > ?";
      Connection con = DBInfo.conn();
      PreparedStatement preparedStatement = con.prepareStatement(query);
      preparedStatement.setString(1, itemName);
      preparedStatement.setInt(2, 0);
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

  public static void addSlip(String itemName, int id) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    try {
      con = DBInfo.conn();

      // Insert the borrow slip
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

      // Update the availability count if the slip was added successfully
      if (rowsAffected > 0) {
        String updateSql = "UPDATE book SET avail = avail - 1 WHERE title = ? AND avail > 0";
        PreparedStatement updateStatement = con.prepareStatement(updateSql);
        updateStatement.setString(1, itemName);
        int updateRowsAffected = updateStatement.executeUpdate();
        System.out.println("Book availability updated! Rows affected: " + updateRowsAffected);

        // Send a notification if the availability update was successful
        if (updateRowsAffected > 0) {
          sendNotification(1000, id, "Đã mượn sách thành công");
        } else {
          System.out.println("Error: The book is no longer available.");
        }

        updateStatement.close();
      }

    } catch (SQLException e) {
      System.out.println("Error adding slip");
      e.printStackTrace();
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
   * muon sach.
   *
   * @param itemName ten sach
   */
  public static void borrowBook(String itemName, int id) {
    try {
      // Check if the book is available in the database
      if (!inDb(itemName)) {
        // Uncomment the following lines if you want to provide user feedback:
        // System.out.println("Cuốn sách bạn muốn mượn không có trong hệ thống của thư viện!!!");
        // sendNotification(1000, id, "Cuốn sách bạn muốn mượn đã hết, vui lòng mượn cuốn khác");
        return;
      }

      // Connect to the database
      Connection con = DBInfo.conn();

      // Check the current availability of the book
      String checkSql = "SELECT avail FROM book WHERE title = ?";
      PreparedStatement checkStatement = con.prepareStatement(checkSql);
      checkStatement.setString(1, itemName);
      ResultSet resultSet = checkStatement.executeQuery();

      if (resultSet.next()) {
        int currentAvail = resultSet.getInt("avail");
        if (currentAvail <= 0) {
          // sendNotification(1000, id, "Cuốn sách bạn muốn mượn đã hết, vui lòng mượn cuốn khác");
          System.out.println("Cuốn sách bạn muốn mượn đã hết, không còn bản nào để mượn.");
          resultSet.close();
          checkStatement.close();
          con.close();
          return;
        }
        addBorrowRequest(itemName, id);
      }

      // Close the check statement and result set
      resultSet.close();
      checkStatement.close();

      // Close the connection
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void addBorrowRequest(String itemName, int id) {
    Connection con = null;
    PreparedStatement checkStmt = null;
    PreparedStatement insertStmt = null;

    try {
      con = DBInfo.conn();

      // Kiểm tra xem yêu cầu đã tồn tại chưa
      String checkSql = "SELECT COUNT(*) FROM borrow_request WHERE user_id = ? AND book_name = ? AND accepted = 0";
      checkStmt = con.prepareStatement(checkSql);
      checkStmt.setInt(1, id);
      checkStmt.setString(2, itemName);

      ResultSet rs = checkStmt.executeQuery();
      if (rs.next() && rs.getInt(1) > 0) {
        System.out.println(
            "Yêu cầu mượn sách đã tồn tại cho user_id = " + id + " và sách '" + itemName + "'.");
        return;
      }
      String insertSql = "INSERT INTO borrow_request (user_id, book_name, borrow_date, return_date) VALUES (?, ?, ?, ?)";
      insertStmt = con.prepareStatement(insertSql);

      LocalDateTime currentDateTime = LocalDateTime.now();
      LocalDateTime dateTimeAfter10Days = currentDateTime.plusDays(10);
      insertStmt.setInt(1, id);
      insertStmt.setString(2, itemName);
      insertStmt.setTimestamp(3, Timestamp.valueOf(currentDateTime));
      insertStmt.setTimestamp(4, Timestamp.valueOf(dateTimeAfter10Days));

      int rowsAffected = insertStmt.executeUpdate();
      System.out.println(
          "Yêu cầu mượn sách đã được thêm thành công! Rows affected: " + rowsAffected);

    } catch (SQLException e) {
      System.out.println("Lỗi khi thêm yêu cầu mượn sách.");
      e.printStackTrace();
    } finally {
      try {
        if (checkStmt != null) {
          checkStmt.close();
        }
        if (insertStmt != null) {
          insertStmt.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


  public static void acceptBorrowRequest(int userId, String bookName) {
    String sql = "UPDATE borrow_request SET accepted = 1 WHERE user_id = ? AND book_name = ?";
    try (Connection conn = DBInfo.conn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setString(2, bookName);

      int rowsUpdated = stmt.executeUpdate();

      if (rowsUpdated > 0) {
        System.out.println("Yêu cầu mượn sách của user_id = " + userId + " cho sách '" + bookName
            + "' đã được chấp nhận.");
        addSlip(bookName, userId);
        sendNotification(1000, userId,
            "Yêu cầu mượn sách của bạn đối với cuốn \"" + bookName + "\" đã được phê duyệt");
        ;
      } else {
        System.out.println(
            "Không tìm thấy yêu cầu mượn sách cho user_id = " + userId + " và sách '" + bookName
                + "'.");
      }
    } catch (SQLException e) {
      System.err.println("Lỗi khi cập nhật yêu cầu mượn sách: " + e.getMessage());
    }
  }

  public static void denyBorrowRequest(int userId, String bookName) {
    String sql = "UPDATE borrow_request SET accepted = 1 WHERE user_id = ? AND book_name = ?";
    try (Connection conn = DBInfo.conn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      stmt.setString(2, bookName);

      int rowsUpdated = stmt.executeUpdate();

      if (rowsUpdated > 0) {
        sendNotification(1000, userId,
            "Yêu cầu mượn sách của bạn đối với cuốn \"" + bookName + "\" đã bị từ chối");
        ;

        System.out.println("Yêu cầu mượn sách của user_id = " + userId + " cho sách '" + bookName
            + "' đã bị từ chối.");
      } else {
        System.out.println(
            "Không tìm thấy yêu cầu mượn sách cho user_id = " + userId + " và sách '" + bookName
                + "'.");
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
      String selectBorrowDateSql = "SELECT borrow_date FROM borrow_slip WHERE book_name = ? AND user_id = ?";
      PreparedStatement selectBorrowDateStatement = con.prepareStatement(selectBorrowDateSql);
      selectBorrowDateStatement.setString(1, itemName);
      selectBorrowDateStatement.setInt(2, id);
      ResultSet borrowDateResult = selectBorrowDateStatement.executeQuery();

      if (borrowDateResult.next()) {
        java.sql.Timestamp borrowDate = borrowDateResult.getTimestamp("borrow_date");
        String deleteSql = "DELETE FROM borrow_slip WHERE book_name = ? AND user_id = ?";
        PreparedStatement deleteStatement = con.prepareStatement(deleteSql);
        deleteStatement.setString(1, itemName);
        deleteStatement.setInt(2, id);
        int rowsAffected = deleteStatement.executeUpdate();

        if (rowsAffected > 0) {
          System.out.println("Xóa khỏi borrow_slip thành công!");
          String insertHistorySql = "INSERT INTO borrow_history (user_id, book_name, borrow_date, return_date) VALUES (?, ?, ?, NOW())";
          PreparedStatement historyStatement = con.prepareStatement(insertHistorySql);
          historyStatement.setInt(1, id);
          historyStatement.setString(2, itemName);
          historyStatement.setTimestamp(3, borrowDate);
          int historyRows = historyStatement.executeUpdate();

          if (historyRows > 0) {
            System.out.println("Thêm vào borrow_history thành công!");
          } else {
            System.out.println("Không thể thêm vào borrow_history.");
          }
          historyStatement.close();
          String updateSql = "UPDATE book SET avail = avail + 1 WHERE title = ?";
          PreparedStatement updateStatement = con.prepareStatement(updateSql);
          updateStatement.setString(1, itemName);
          int rowsUpdated = updateStatement.executeUpdate();

          if (rowsUpdated > 0) {
            System.out.println("Thay đổi trạng thái thành công, dòng bị ảnh hưởng: " + rowsUpdated);
            sendNotification(1000, id, "Trả sách thành công");
          } else {
            System.out.println("Thay đổi trạng thái không thành công.");
          }

          updateStatement.close();
        } else {
          System.out.println("Không tìm thấy người dùng trong borrow_slip với cuốn: " + itemName);
        }

        deleteStatement.close();
      } else {
        System.out.println("Không tìm thấy borrow_date trong borrow_slip cho sách: " + itemName);
      }

      borrowDateResult.close();
      selectBorrowDateStatement.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi trả sách.");
    }
  }


  public static void editBook(Book book, String newTitle, String newAuthor, String newPublisher,
      String newThumbnail, String newDescription, String newPageNum, String newLanguage) {
    String oldTitle = book.getTitle();

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

  public static void addCategory(String categoryName) {
    String checkSql = "SELECT cnt FROM category WHERE name = ?";
    String updateCntSql = "UPDATE category SET cnt = cnt + 1 WHERE name = ?";
    String insertSql = "INSERT INTO category (name, cnt) VALUES (?, 1)";
    String updateAllSql = "UPDATE category SET cnt = cnt + 1 WHERE name = 'ALL'";

    try (Connection con = DBInfo.conn()) {
      // Kiểm tra danh mục có tồn tại hay không
      try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
        checkStmt.setString(1, categoryName);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
          try (PreparedStatement updateStmt = con.prepareStatement(updateCntSql)) {
            updateStmt.setString(1, categoryName);
            updateStmt.executeUpdate();
          }
          System.out.println("Category exists. Incremented cnt for: " + categoryName);
        } else {
          try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
            insertStmt.setString(1, categoryName);
            insertStmt.executeUpdate();
          }
          System.out.println("Category not found. Added new category: " + categoryName);
        }
      }
      // Tăng cnt của danh mục "ALL"
      try (PreparedStatement updateAllStmt = con.prepareStatement(updateAllSql)) {
        updateAllStmt.executeUpdate();
      }
      System.out.println("Incremented cnt for category: ALL");

    } catch (SQLException e) {
      System.out.println("Error adding or updating category");
      e.printStackTrace();
    }
  }

  public static void deleteOneNotification(Notification notification) {
    int senderId = notification.getSenderId();
    int receiverId = notification.getReceiverId();
    String message = notification.getMessage();
    String sql = "DELETE FROM notifications WHERE sender_id = ? AND receiver_id = ? AND message = ? LIMIT 1";

    try (Connection connection = DBInfo.conn();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setInt(1, senderId);
      preparedStatement.setInt(2, receiverId);
      preparedStatement.setString(3, message);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Xoa thong bao thanh cong");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void deleteCategory(String categoryName) {
    String checkSql = "SELECT cnt FROM category WHERE name = ?";
    String updateCntSql = "UPDATE category SET cnt = cnt - 1 WHERE name = ?";
    String deleteSql = "DELETE FROM category WHERE name = ?";
    String updateAllSql = "UPDATE category SET cnt = cnt - 1 WHERE name = 'ALL'"; // Giảm cnt của ALL

    try (Connection con = DBInfo.conn()) {
      // Kiểm tra danh mục có tồn tại không
      try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
        checkStmt.setString(1, categoryName);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
          int cnt = rs.getInt("cnt");

          // Nếu cnt > 1, chỉ cần giảm cnt của danh mục đó đi 1
          if (cnt > 1) {
            try (PreparedStatement updateStmt = con.prepareStatement(updateCntSql)) {
              updateStmt.setString(1, categoryName);
              updateStmt.executeUpdate();
              System.out.println("Decreased cnt for category: " + categoryName);
            }
          } else if (cnt == 1) {
            // Nếu cnt == 1, xóa danh mục đó
            try (PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {
              deleteStmt.setString(1, categoryName);
              int rowsAffected = deleteStmt.executeUpdate();
              System.out.println("Category deleted successfully! Rows affected: " + rowsAffected);
            }
            System.out.println("Deleted category as cnt reached 0: " + categoryName);
          }
        } else {
          System.out.println("Category not found. No deletion performed.");
        }
      }

      // Giảm cnt của danh mục "ALL" trong mọi trường hợp
      try (PreparedStatement updateAllStmt = con.prepareStatement(updateAllSql)) {
        updateAllStmt.executeUpdate();
        System.out.println("Decreased cnt for category: ALL");
      }

    } catch (SQLException e) {
      System.out.println("Error deleting or updating category");
      e.printStackTrace();
    }
  }

  /**
   * Adds a book to the database. If a book with the same title already exists, its availability
   * count will be incremented and the record updated. Otherwise, a new book record is inserted.
   *
   * @param book the book to be added, encapsulated in a {@link Book} object.
   */
  public static void addBook(Book book) {
    try {
      // Connect to the database
      Connection con = DBInfo.conn();

      // Check if the book with the same title already exists
      String checkSql = "SELECT avail FROM book WHERE title = ?";
      PreparedStatement checkStatement = con.prepareStatement(checkSql);
      checkStatement.setString(1, book.getTitle());
      ResultSet resultSet = checkStatement.executeQuery();

      if (resultSet.next()) {
        // If the book exists, increment the availability count
        int currentAvail = resultSet.getInt("avail");
        System.out.println(
            "A book with the same title already exists. Updating the existing book...");

        String updateSql =
            "UPDATE book SET authors = ?, publisher = ?, publishedDate = ?, thumbnail = ?, ISBN = ?, "
                + "description = ?, numPage = ?, category = ?, price = ?, language = ?, buyLink = ?, avail = ?, rating = ? "
                + "WHERE title = ?";
        PreparedStatement updateStatement = con.prepareStatement(updateSql);
        updateStatement.setString(1, book.getAuthors());
        updateStatement.setString(2, book.getPublisher());
        updateStatement.setString(3, book.getPublishedDate());
        updateStatement.setString(4, book.getThumbnail());
        updateStatement.setString(5, book.getISBN());
        updateStatement.setString(6, book.getDescription());
        updateStatement.setString(7, book.getNumPage());
        updateStatement.setString(8, book.getCategory());
        updateStatement.setString(9, book.getPrice());
        updateStatement.setString(10, book.getLanguage());
        updateStatement.setString(11, book.getBuyLink());
        updateStatement.setInt(12, currentAvail + 1); // Increment avail by 1
        updateStatement.setInt(13, book.getRating());
        updateStatement.setString(14, book.getTitle());

        int rowsUpdated = updateStatement.executeUpdate();
        System.out.println("Book updated successfully! Rows updated: " + rowsUpdated);
        updateStatement.close();
      } else {
        // If the book doesn't exist, insert a new record
        String sql =
            "INSERT INTO book (title, authors, publisher, publishedDate, thumbnail, ISBN, description, numPage, category, price, language, buyLink, avail, rating) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthors());
        preparedStatement.setString(3, book.getPublisher());
        preparedStatement.setString(4, book.getPublishedDate());
        preparedStatement.setString(5, book.getThumbnail());
        preparedStatement.setString(6, book.getISBN());
        preparedStatement.setString(7, book.getDescription());
        preparedStatement.setString(8, book.getNumPage());
        preparedStatement.setString(9, book.getCategory());
        preparedStatement.setString(10, book.getPrice());
        preparedStatement.setString(11, book.getLanguage());
        preparedStatement.setString(12, book.getBuyLink());
        preparedStatement.setInt(13, 1); // Initial availability count
        preparedStatement.setInt(14, book.getRating());
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Book added successfully! Rows affected: " + rowsAffected);
        preparedStatement.close();
      }
      checkStatement.close();
      resultSet.close();
      addCategory(book.getCategory());
      addPublisher(book.getPublisher());
      addAuthor(book.getAuthors());
      con.close();
    } catch (SQLException e) {
      System.out.println("Error adding or updating book");
      e.printStackTrace();
    }
  }

  public static boolean isBorrowed(String title) {
    String query = "SELECT COUNT(*) FROM borrow_slip WHERE book_name = ?";
    try (Connection connection = DBInfo.conn();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, title);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          int count = resultSet.getInt(1);
          return count > 0;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static ArrayList<Integer> getUserIdListRequestToBorrow(String book_title) {
    ArrayList<Integer> userIdList = new ArrayList<>();
    String query = "SELECT br.user_id " +
        "FROM borrow_request br WHERE br.book_name = ? AND br.accepted = 0";

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, book_title);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        userIdList.add(rs.getInt("user_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return userIdList;
  }

  /**
   * Xóa sách khỏi hệ thống. Nếu sách không tồn tại hoặc đang được mượn, việc xóa sẽ không thực
   * hiện. Gửi thông báo tới các người dùng đã yêu cầu mượn sách bị xóa.
   *
   * @param book đối tượng sách cần xóa, được đại diện bởi {@link Book}.
   */
  public static void deleteBook(Book book) {
    try {
      Connection con = DBInfo.conn();

      // Kiểm tra nếu sách không tồn tại trong hệ thống
      if (!inDb(book.getTitle())) {
        System.out.println("Sách không có trên hệ thống");
        con.close();
        return;
      }

      // Kiểm tra nếu sách đang được mượn
      if (isBorrowed(book.getTitle())) {
        System.out.println("Sách đang được cho mượn");
        con.close();
        return;
      }

      // Lấy danh sách ID người dùng đã yêu cầu mượn sách này
      ArrayList<Integer> userIdList = getUserIdListRequestToBorrow(book.getTitle());
      System.out.println(userIdList.size() + " người dùng đã yêu cầu mượn sách này");

      // Gửi thông báo đến các người dùng
      for (Integer id : userIdList) {
        sendNotification(
            1000,
            id,
            "Cuốn sách \"" + book.getTitle()
                + "\" mà bạn yêu cầu mượn đã không còn trên hệ thống. Mong bạn thông cảm!!!"
        );
      }

      // Thực hiện xóa sách trong cơ sở dữ liệu
      String sql = "DELETE FROM book WHERE title = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, book.getTitle());
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Đã xóa sách thành công! Số hàng bị ảnh hưởng: " + rowsAffected);

      // Xóa các thông tin liên quan đến sách: thể loại, nhà xuất bản, tác giả
      deleteCategory(book.getCategory());
      deletePublisher(book.getPublisher());
      deleteAuthor(book.getAuthors());

      preparedStatement.close();
      con.close();
    } catch (SQLException e) {
      System.out.println("Lỗi khi xóa sách");
      e.printStackTrace();
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
      String usertype, String MSV) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO registration(id,name, username, password, usertype, MSV) VALUE (?,?,?,?,?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      int x = getUserCount() + 1;
      System.out.println(x);
      String hashedPassword = PasswordUtils.hashPassword(password);
      preparedStatement.setInt(1, x);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, username);
      preparedStatement.setString(4, hashedPassword);
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
   * @param username      us
   * @param plainPassword pas
   * @return true/false
   */
  public static boolean checkPass(String username, String plainPassword) {
    String sql = "SELECT password, usertype FROM registration WHERE username = ? AND is_banned = 0";
    try {
      Connection con = DBInfo.conn();
      if (con == null) {
        System.out.println("Không kết nối được với cơ sở dữ liệu!");
        return false;
      }
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String hashedPassword = resultSet.getString("password");
        String userType = resultSet.getString("usertype");

        if (PasswordUtils.verifyPassword(plainPassword, hashedPassword)) {
          System.out.println("Đăng nhập thành công! Loại người dùng: " + userType);
          return true;
        } else {
          System.out.println("Mật khẩu không chính xác!");
        }
      } else {
        System.out.println("Không tìm thấy người dùng hoặc tài khoản bị khóa!");
      }
      preparedStatement.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
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
        String password = PasswordUtils.hashPassword(newPassword);
        preparedStatement.setString(paramIndex++, password);
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
        String password = PasswordUtils.hashPassword(newPassword);
        preparedStatement.setString(paramIndex++, password);
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

        String publishedDate = resultSet.getString("publishedDate");
        book.setPublishedDate(publishedDate.toString());

        book.setThumbnail(resultSet.getString("thumbnail"));
        book.setISBN(resultSet.getString("ISBN"));
        book.setDescription(resultSet.getString("description"));
        book.setNumPage(resultSet.getString("numPage"));
        book.setCategory(resultSet.getString("category"));
        book.setPrice(resultSet.getString("price"));
        book.setLanguage(resultSet.getString("language"));
        book.setBuyLink(resultSet.getString("buyLink"));
        book.setAvail(resultSet.getInt("avail"));
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
        int avail = resultSet.getInt("avail");
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

  public static void addComment(Comment comment) {
    try {
      Connection conn = DBInfo.conn();
      String bookTitle = comment.getBookTitle();
      String username = comment.getUsername();
      MyDateTime time = comment.getTime();
      String content = comment.getContent();
      int rate = comment.getRate();
      String sql = "INSERT INTO comment (book_title, username, time, content,rate) VALUES (?, ?, ?, ?,?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, bookTitle);
      stmt.setString(2, username);
      stmt.setTimestamp(3,
          Timestamp.valueOf(time.toLocalDateTime()));
      stmt.setString(4, content);
      stmt.setInt(5, rate);
      rateBook(bookTitle, rate);
      int rowsInserted = stmt.executeUpdate();
      if (rowsInserted > 0) {
        System.out.println("Thêm bình luận thành công!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static ArrayList<Comment> getCommentList(Book book) {
    ArrayList<Comment> ret = new ArrayList<>();
    try {
      String bookTitle = book.getTitle();
      Connection conn = DBInfo.conn();
      String sql = "SELECT * FROM comment WHERE book_title = ?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, bookTitle);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String username = resultSet.getString("username");
        LocalDateTime time = resultSet.getTimestamp("time")
            .toLocalDateTime();
        String content = resultSet.getString("content");
        int rate = resultSet.getInt("rate");
        Comment comment = new Comment(
            bookTitle,
            username,
            new MyDateTime(time),
            content,
            rate
        );

        ret.add(comment);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }


  public static void ban(User X) {
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

  public static void unBan(User X) {
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

  public static ArrayList<Book> getBookListByNumView() {
    ArrayList<Book> bookList = new ArrayList<>();
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      con = DBInfo.conn();
      final String sql = "SELECT * FROM book ORDER BY numView DESC";
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
        int avail = resultSet.getInt("avail");
        int rating = resultSet.getInt("rating");
        int numView = resultSet.getInt("numView");
        Book book = new Book(title, ISBN, Author, Publisher, publishedDate,
            description, thumbnail, numPage, Category, price,
            language, buyLink, avail, rating);
        book.setNumView(numView);
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

  public static void updView(Book book) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String name = book.getTitle();
    try {
      con = DBInfo.conn();
      String sql = "UPDATE book SET numView = numView + 1 WHERE title = ?";
      preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, name);

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("View count updated successfully for: " + name);
      } else {
        System.out.println("No book found with the title: " + name);
      }
    } catch (SQLException e) {
      System.out.println("Error updating view count for book: " + name);
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

  public static String getAuthor(String bookTitle) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String authorName = null;

    try {
      con = DBInfo.conn();
      String sql = "SELECT authors FROM book WHERE title = ?";
      preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, bookTitle);

      // Execute the query
      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        authorName = resultSet.getString("authors");
      } else {
        System.out.println("No book found with the title: " + bookTitle);
      }

    } catch (SQLException e) {
      System.out.println("Error while fetching author name");
      e.printStackTrace();
    } finally {
      // Ensure resources are closed
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

    return authorName;
  }

  public static boolean validUsername(String username) {
    if (username == null || username.isEmpty()) {
      return false;
    }
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    return username.matches(emailRegex);
  }

  public static boolean validPassword(String password) {
    return password.length() >= 8;
  }

  public static void main(String[] args) throws Exception {
    //  Register(1,"nguyenvana","nguyenvana@gmail.com","password123","user","1");
    // Register(1,"tranthib","tranthib@gmail.com","password234","user","1");
    // Register(1,"abc","23020158@vnu.edu.vn","password345","user","1");
    // Register(1,"bcd","23020161@vnu.edu.vn","password111","user","1");
    //Register(1,"admin","levanc","password789","admin","1");

    //PasswordRecoveryService.sendToken("23020158@vnu.edu.vn");
    System.out.println(PasswordRecoveryService.checkToken("23020158@vnu.edu.vn", "1441"));
  }
}

