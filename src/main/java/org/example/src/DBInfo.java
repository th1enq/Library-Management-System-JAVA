package org.example.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class DBInfo {

  /**
   * static { try { Class.forName("com.mysql.jdbc.Driver"); System.out.println("Driver Loaded...");
   * } catch (ClassNotFoundException e) { e.printStackTrace(); } }
   */

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

  public static String getNotice()
  {
    Connection con=DBInfo.conn();
    String query="SELECT * FROM notice";
    value="";
    try {
      PreparedStatement ps=con.prepareStatement(query);
      ResultSet res=ps.executeQuery();
      while(res.next())
      {
        value=res.getString(2);
        System.out.println("previous Notice is "+value);
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
      String query = "SELECT * FROM `book` WHERE title=" + itemName;
      Connection con = DBInfo.conn();
      PreparedStatement ps = con.prepareStatement(query);
      ResultSet resultSet = ps.executeQuery(query);
      if (resultSet.next()) {
        return true;
      }
      resultSet.close();
      ps.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * muon sach.
   *
   * @param itemName ten sach
   */
  public static void borrowBook(String itemName) {
    try {
      String query = "DELETE FROM `book` WHERE title=?";
      Connection con = DBInfo.conn();
      PreparedStatement preparedStatement = con.prepareStatement(query);
      preparedStatement.setString(1, itemName);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Delete successful, " + rowsAffected + " row(s) deleted.");
        String sql = "INSERT INTO borrow_slip (slip_id,user_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement2 = con.prepareStatement(sql);
        int slipId = 0;
        int userId = 1;
        int bookId = 2;
        String borrowDate = "2024-10-01";
        String returnDate = "2024-10-15";
        preparedStatement2.setInt(1, slipId);
        preparedStatement2.setInt(2, userId);
        preparedStatement2.setInt(3, bookId);
        preparedStatement2.setString(4, borrowDate);
        preparedStatement2.setString(5, returnDate);
        int rowsAffected2 = preparedStatement2.executeUpdate();
        if (rowsAffected2 > 0) {
          System.out.println("Borrow slip added successfully.");
        } else {
          System.out.println("No rows were inserted.");
        }

      } else {
        System.out.println("No rows were deleted.");
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
  public static void returnBook() {



  }


  public static void main(String[] args) {
    DBInfo.borrowBook("The Elegant Universe");
  }
}
