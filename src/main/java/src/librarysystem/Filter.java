package src.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp này cung cấp các phương thức để tìm kiếm và lọc sách và người dùng từ cơ sở dữ liệu.
 * Các phương thức này sử dụng các tham số đầu vào để tìm kiếm sách hoặc người dùng theo các thuộc tính như tiêu đề, tác giả, thể loại, v.v.
 */
public class Filter {

  private static Filter instance;

  /**
   * Phương thức này đảm bảo chỉ có một đối tượng Filter duy nhất tồn tại (singleton pattern).
   *
   * @return Một thể hiện duy nhất của Filter.
   */
  public static Filter getInstance() {
    if (instance == null) {
      synchronized (Filter.class) {
        if (instance == null) {
          instance = new Filter();
        }
      }
    }
    return instance;
  }

  /**
   * Tìm kiếm các sách trong cơ sở dữ liệu theo một phần chuỗi trong tiêu đề.
   *
   * @param substr Chuỗi con để tìm kiếm trong tiêu đề sách.
   * @return Danh sách các sách có tiêu đề chứa chuỗi con đã cho.
   */
  public ArrayList<Book> getBookByTitleSubstr(String substr) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Book> ret = new ArrayList<>();
    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE title like '%" + substr + "%'";
      preparedStatement = con.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
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
        ret.add(book);
      }
      if (ret.isEmpty()) {
        System.out.println("Không tìm thấy sách có tên: " + substr);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi lấy thông tin sách");
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
    return ret;
  }

  /**
   * Tìm kiếm các sách trong cơ sở dữ liệu theo một phần chuỗi trong tên tác giả.
   *
   * @param substr Chuỗi con để tìm kiếm trong tên tác giả của sách.
   * @return Danh sách các sách có tên tác giả chứa chuỗi con đã cho.
   */
  public ArrayList<Book> getBookByAuthorSubstr(String substr) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Book> ret = new ArrayList<>();
    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE authors like '%" + substr + "%'";
      preparedStatement = con.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
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
        book.setAvail(resultSet.getInt("avail"));
        book.setRating(resultSet.getInt("rating"));
        ret.add(book);
      }
      if (ret.isEmpty()) {
        System.out.println("Không tìm thấy sách có tên: " + substr);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi lấy thông tin sách");
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
    return ret;
  }

  /**
   * Tìm kiếm các sách trong cơ sở dữ liệu theo một phần chuỗi trong thể loại sách.
   *
   * @param substr Chuỗi con để tìm kiếm trong thể loại sách.
   * @return Danh sách các sách có thể loại chứa chuỗi con đã cho.
   */
  public ArrayList<Book> getBookByCategorySubstr(String substr) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Book> ret = new ArrayList<>();
    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE category like '%" + substr + "%' ";
      preparedStatement = con.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
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
        ret.add(book);
      }
      if (ret.isEmpty()) {
        System.out.println("Không tìm thấy sách có tên: " + substr);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi lấy thông tin sách");
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
    return ret;
  }

  /**
   * Tìm kiếm người dùng trong cơ sở dữ liệu theo một phần chuỗi trong tên người dùng hoặc tên đầy đủ,
   * và có thể lọc theo loại người dùng (ví dụ: "ALL", "ADMIN", "USER").
   *
   * @param tmp Chuỗi con để tìm kiếm trong tên người dùng hoặc tên đầy đủ.
   * @param type Loại người dùng cần lọc (ví dụ: "ALL", "ADMIN", "USER").
   * @return Danh sách các người dùng có tên hoặc tên người dùng chứa chuỗi con đã cho,
   *         và phù hợp với loại người dùng nếu có.
   */
  public ArrayList<User> getUserBySubstr(String tmp, String type) {
    ArrayList<User> userList = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM registration WHERE name LIKE ?");

    if (!"ALL".equalsIgnoreCase(type)) {
      sql.append(" AND usertype = ?");
    }

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

      stmt.setString(1, "%" + tmp + "%");

      if (!"ALL".equalsIgnoreCase(type)) {
        stmt.setString(2, type);
      }

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
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

        User user = new User(id, name, username, password, userType, isBanned, avatarLink, MSV,
            university, phone, coverPhotoLink, reputation);
        userList.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return userList;
  }

  /**
   * Trả về danh sách tất cả người dùng trong cơ sở dữ liệu hoặc lọc theo loại người dùng.
   *
   * @param tmp Loại người dùng cần lọc (ví dụ: "ALL", "ADMIN", "USER").
   * @return Danh sách tất cả người dùng hoặc các người dùng có loại phù hợp.
   */
  public static ArrayList<User> getUserList(String tmp) {
    ArrayList<User> userList = new ArrayList<>();
    String sql = "SELECT * FROM registration";

    if (!tmp.equals("ALL")) {
      sql += " WHERE usertype = ?";
    }

    try (Connection conn = DBInfo.conn();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      if (!tmp.equals("ALL")) {
        stmt.setString(1, tmp);
      }

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
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

        User user = new User(id, name, username, password, userType, isBanned, avatarLink, MSV,
            university, phone, coverPhotoLink, reputation);
        userList.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return userList;
  }
}
