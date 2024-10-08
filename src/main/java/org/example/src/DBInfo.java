package org.example.src;

import java.awt.desktop.SystemEventListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.event.UndoableEditEvent;
import java.sql.Date;

public class DBInfo {

  public static String curUsername = "";
  public static String curPass = "";
  public static int curId = 0;
  private static int numUser = 6;

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      System.out.println("Driver Loaded...");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  public static Connection conn() {
    Connection con = null;
    try {
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TESTT", "root", "");
      System.out.println("Connection Established...");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return con;
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

  public static void searchBooks_by(String itemName, String values) throws SQLException {

  }

  public static Vector<Vector> outerVector2;
  public static Vector colsName2;

  public static void viewLibrarians(String usertype) throws SQLException {

  }

  public static String value;

  public static String getNotice() {
    Connection con = DBInfo.conn();
    String query = "SELECT * FROM notice";
    value = "";
    try {
      PreparedStatement ps = con.prepareStatement(query);
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        value = res.getString(2);
        System.out.println("previous Notice is " + value);
      }
    } catch (SQLException e2) {
      e2.printStackTrace();
    }
    return value;
  }

  /**
   * check xem trong Database co quyen sach can tim ko?.
   *
   * @param itemName ten quyen sach can tim
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

  public static void addSlip(String itemName) {
    try {
      Connection con = DBInfo.conn();
      // Câu lệnh SQL đã sửa
      String sql = "INSERT INTO borrow_slip(user_id, book_name, borrow_date, return_date) VALUES (?, ?, ?, ?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);

      // Lấy ngày hiện tại và ngày sau 10 ngày
      LocalDate currentDate = LocalDate.now();
      LocalDate dateAfter10Days = currentDate.plusDays(10);

      // Thiết lập giá trị cho các tham số
      preparedStatement.setInt(1, curId); // user_id
      preparedStatement.setString(2, itemName); // book_name
      preparedStatement.setDate(3, Date.valueOf(currentDate)); // borrow_date
      preparedStatement.setDate(4, Date.valueOf(dateAfter10Days)); // return_date

      // Thực hiện chèn dữ liệu
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Slip added successfully! Rows affected: " + rowsAffected);

      // Đóng PreparedStatement và Connection
      preparedStatement.close();
      con.close();
    } catch (SQLException e) {
      System.out.println("Error adding slip");
      e.printStackTrace();
    }
  }

  /**
   * muon sach.
   *
   * @param itemName ten sach
   */
  public static void borrowBook(String itemName) {
    try {
      Connection con = DBInfo.conn();
      String sql = "UPDATE book SET avail = ? WHERE title = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, "NO");
      preparedStatement.setString(2, itemName);
      int rowAffected = preparedStatement.executeUpdate();
      if (rowAffected > 0) {
        System.out.println("Thay doi trang thai dong" + rowAffected);
        addSlip(itemName);
      }
      preparedStatement.close();
      con.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * tra sach
   */
  public static void returnBook(String itemName) {

    Connection con = DBInfo.conn();
    try {

      String sql = "DELETE FROM borrow_slip WHERE book_name = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, itemName);
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

  public static void addSubject(String a) {
    try {
      Connection con = DBInfo.conn();
      String sql = "INSERT INTO subject(name) VALUE (?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("subject added successfully! Rows affected: " + rowsAffected);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error adding subject");
      EE.printStackTrace();
    }
  }

  public static void addBook(String a, String b, String c, String d, String e) {
    try {
      Connection con = DBInfo.conn();
      if (inDb(b)) {
        System.out.println("da co quyen sach nay roi");
        return;
      }
      String sql = "INSERT INTO book(bookid, title, author, subject, publisher) VALUE (?,?,?,?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(sql);
      preparedStatement.setString(1, a);
      preparedStatement.setString(2, b);
      preparedStatement.setString(3, c);
      preparedStatement.setString(4, d);
      preparedStatement.setString(5, e);
      int rowsAffected = preparedStatement.executeUpdate();
      System.out.println("Book added successfully! Rows affected: " + rowsAffected);
      addAuthor(c);
      addSubject(d);
      addPublisher(e);
      preparedStatement.close();
      con.close();
    } catch (SQLException EE) {
      System.out.println("Error adding book");
      EE.printStackTrace();
    }
  }

  /**
   * dang li nguoi moi.
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
      numUser += 1;
      preparedStatement.setInt(1, numUser);
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

  /**
   * kiem tra mat khau
   *
   * @param username us
   * @param password pas
   * @return true/false
   */
  public static boolean checkPass(String username, String password) {
    String sql = "SELECT usertype FROM registration WHERE username = ? AND password = ?";

    try {
      Connection con = DBInfo.conn();
      if (con == null) {
        System.out.println("Ngu");
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
    if (checkPass(Username, Password) == true) {
      curPass = Password;
      curUsername = Username;
      curId = findUserId(Username, Password);
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

  /**
   * chinh sua thong tin nguoi dung.
   *
   * @param id          id
   * @param newUsername ten
   * @param newPassword mk
   */
  public static void updateUser(int id, String newUsername, String newPassword) {
    Connection con = DBInfo.conn();
    try {

      String sql = "UPDATE registration SET username = ?, password = ? WHERE id = ?";
      PreparedStatement preparedStatement = con.prepareStatement(sql);

      preparedStatement.setString(1, newUsername);
      preparedStatement.setString(2, newPassword);
      preparedStatement.setInt(3, id);
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
      System.out.println("Loi sua user va pass");
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

  public static void main(String[] args) {
    DBInfo.rateBook("1984", 5);
  }

}
