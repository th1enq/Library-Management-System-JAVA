package src.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Filter {

  private static Filter instance;

  private Filter() {}

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
  public ArrayList<Book> getBookByTitleSubstr(String substr) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Book> ret = new ArrayList<>();
    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE title like '%" + substr +"%'";
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
        book.setAvail(resultSet.getString("avail"));
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
  public ArrayList<Book> getBookByAuthorSubstr(String substr) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Book> ret = new ArrayList<>();
    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE authors like '%" + substr +"%'";
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
        book.setAvail(resultSet.getString("avail"));
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

  public ArrayList<Book> getBookByCategorySubstr(String substr) {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ArrayList<Book> ret = new ArrayList<>();
    try {
      con = DBInfo.conn();
      String sql = "SELECT * FROM book WHERE category like '%" + substr +"%'";
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
        book.setAvail(resultSet.getString("avail"));
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

}
