package src.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookIssueDB {

  public static ArrayList<BookIssue> getProcessingList() {
    ArrayList<BookIssue> ret = new ArrayList<>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = DBInfo.conn();
      String sql = "SELECT user_id, book_name, borrow_date, return_date FROM borrow_request WHERE accepted = 0";
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        boolean okToAdd = false;
        int userId = rs.getInt("user_id");
        String bookTitle = rs.getString("book_name");
        MyDateTime issueDate = new MyDateTime(rs.getTimestamp("borrow_date").toLocalDateTime());
        MyDateTime returnDate = new MyDateTime(rs.getTimestamp("return_date").toLocalDateTime());
        BookIssue bookIssue = new BookIssue();
        bookIssue.setUserId(userId);
        bookIssue.setBookTitle(bookTitle);
        bookIssue.setIssueDate(issueDate);
        bookIssue.setReturnDate(returnDate);
        bookIssue.setBookAuthor(DBInfo.getAuthor(bookTitle));
        if (DBInfo.getUserById(userId) != null) {
          bookIssue.setUsername(DBInfo.getUserById(userId).getUsername());
          okToAdd = true;
        }
        bookIssue.setStatus(BookIssue.STATUS_PROCESSING);
        if (okToAdd) {
          ret.add(bookIssue);
        }
      }
    } catch (SQLException e) {
      System.out.println("Lỗi khi lấy danh sách processing:");
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
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

  public static ArrayList<BookIssue> getDelayList() {
    ArrayList<BookIssue> ret = new ArrayList<>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = DBInfo.conn();
      String sql = "SELECT user_id, book_name, borrow_date, return_date FROM borrow_slip  WHERE return_date < NOW()";
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        boolean okToAdd = false;
        int userId = rs.getInt("user_id");
        String bookTitle = rs.getString("book_name");
        MyDateTime issueDate = new MyDateTime(rs.getTimestamp("borrow_date").toLocalDateTime());
        MyDateTime returnDate = new MyDateTime(rs.getTimestamp("return_date").toLocalDateTime());
        BookIssue bookIssue = new BookIssue();
        bookIssue.setUserId(userId);
        bookIssue.setBookTitle(bookTitle);
        bookIssue.setIssueDate(issueDate);
        bookIssue.setReturnDate(returnDate);
        bookIssue.setBookAuthor(DBInfo.getAuthor(bookTitle));
        if (DBInfo.getUserById(userId) != null) {
          bookIssue.setUsername(DBInfo.getUserById(userId).getUsername());
          okToAdd = true;
        }
        bookIssue.setStatus(BookIssue.STATUS_DELAY);

        if (okToAdd) {
          ret.add(bookIssue);
        }
      }
    } catch (SQLException e) {
      System.out.println("Lỗi khi lấy danh sách delay:");
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
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

  public static ArrayList<BookIssue> getReturnedList() {
    ArrayList<BookIssue> ret = new ArrayList<>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = DBInfo.conn();
      String sql = "SELECT user_id, book_name, borrow_date, return_date FROM borrow_history";
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        int userId = rs.getInt("user_id");
        boolean okToAdd = false;
        String bookTitle = rs.getString("book_name");
        MyDateTime issueDate = new MyDateTime(rs.getTimestamp("borrow_date").toLocalDateTime());
        MyDateTime returnDate = new MyDateTime(rs.getTimestamp("return_date").toLocalDateTime());
        BookIssue bookIssue = new BookIssue();
        bookIssue.setUserId(userId);
        bookIssue.setBookTitle(bookTitle);
        bookIssue.setIssueDate(issueDate);
        bookIssue.setReturnDate(returnDate);
        bookIssue.setBookAuthor(DBInfo.getAuthor(bookTitle));
        if (DBInfo.getUserById(userId) != null) {
          bookIssue.setUsername(DBInfo.getUserById(userId).getUsername());
          okToAdd = true;
        }
        bookIssue.setStatus(BookIssue.STATUS_RETURNED);

        if (okToAdd) {
          ret.add(bookIssue);
        }
      }
    } catch (SQLException e) {
      System.out.println("Lỗi khi lấy danh sách delay:");
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
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

  public static ArrayList<BookIssue> getLateList() {
    ArrayList<BookIssue> ret = new ArrayList<>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = DBInfo.conn();

      String sql = "SELECT user_id, book_name, borrow_date, return_date FROM borrow_history WHERE DATEDIFF(return_date, borrow_date) > 10";
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        boolean okToAdd = false;
        int userId = rs.getInt("user_id");
        String bookTitle = rs.getString("book_name");
        MyDateTime issueDate = new MyDateTime(rs.getTimestamp("borrow_date").toLocalDateTime());
        MyDateTime returnDate = new MyDateTime(rs.getTimestamp("return_date").toLocalDateTime());
        BookIssue bookIssue = new BookIssue();
        bookIssue.setUserId(userId);
        bookIssue.setBookTitle(bookTitle);
        bookIssue.setIssueDate(issueDate);
        bookIssue.setReturnDate(returnDate);
        bookIssue.setBookAuthor(DBInfo.getAuthor(bookTitle));
        if (DBInfo.getUserById(userId) != null) {
          bookIssue.setUsername(DBInfo.getUserById(userId).getUsername());
          okToAdd = true;
        }
        long daysLate =
            java.time.Duration.between(issueDate.toLocalDateTime(), returnDate.toLocalDateTime())
                .toDays() - 10;
        if (daysLate > 1) {
          bookIssue.setStatus("LATE " + daysLate + " DAYS");
        } else {
          bookIssue.setStatus("LATE 1 DAY");
        }
        if (okToAdd) {
          ret.add(bookIssue);
        }
      }
    } catch (SQLException e) {
      System.out.println("Lỗi khi lấy danh sách sách trả muộn:");
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
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


  public static ArrayList<BookIssue> getBorrowedList() {
    ArrayList<BookIssue> ret = new ArrayList<>();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = DBInfo.conn();
      String sql = "SELECT user_id, book_name, borrow_date, return_date FROM borrow_slip WHERE return_date > NOW()";
      pstmt = con.prepareStatement(sql);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        boolean okToAdd = false;
        int userId = rs.getInt("user_id");
        String bookTitle = rs.getString("book_name");
        MyDateTime issueDate = new MyDateTime(rs.getTimestamp("borrow_date").toLocalDateTime());
        MyDateTime returnDate = new MyDateTime(rs.getTimestamp("return_date").toLocalDateTime());
        BookIssue bookIssue = new BookIssue();
        bookIssue.setUserId(userId);
        bookIssue.setBookTitle(bookTitle);
        bookIssue.setIssueDate(issueDate);
        bookIssue.setReturnDate(returnDate);
        bookIssue.setBookAuthor(DBInfo.getAuthor(bookTitle));
        if (DBInfo.getUserById(userId) != null) {
          bookIssue.setUsername(DBInfo.getUserById(userId).getUsername());
          okToAdd = true;
        }
        bookIssue.setStatus("BORROWED");
        if (okToAdd) {
          ret.add(bookIssue);
        }
      }
    } catch (SQLException e) {
      System.out.println("Lỗi khi lấy danh sách dang muon:");
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
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

  public static ArrayList<BookIssue> getTotalList() {
    ArrayList<BookIssue> ret = new ArrayList<>();
    ArrayList<BookIssue> ret1 = getProcessingList();
    ret.addAll(ret1);
    ArrayList<BookIssue> ret2 = getDelayList();
    ret.addAll(ret2);
    ArrayList<BookIssue> ret3 = getReturnedList();
    ret.addAll(ret3);
    ArrayList<BookIssue> ret4 = getLateList();
    ret.addAll(ret4);
    ArrayList<BookIssue> ret5 = getBorrowedList();
    ret.addAll(ret5);
    return ret;
  }

}
