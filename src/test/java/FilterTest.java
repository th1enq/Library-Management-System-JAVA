import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import src.librarysystem.Book;
import src.librarysystem.Filter;
import src.librarysystem.User;

class FilterTest {

  // Test the singleton instance
  @Test
  void testSingletonInstance() {
    Filter instance1 = Filter.getInstance();
    Filter instance2 = Filter.getInstance();
    assertSame(instance1, instance2, "Filter should follow singleton pattern.");
  }

  // Test getBookByTitleSubstr method
  @Test
  void testGetBookByTitleSubstr() {
    String searchKeyword = "Java";
    ArrayList<Book> books = Filter.getInstance().getBookByTitleSubstr(searchKeyword);

    // assertNotNull(books, "The result should not be null.");
    //assertTrue(books.size() > 0, "The result should return at least one book if matching entries exist.");

    // Check if all returned books have the title containing the search keyword
    for (Book book : books) {
      assertTrue(book.getTitle().toLowerCase().contains(searchKeyword.toLowerCase()),
          "Book title should contain the search keyword.");
    }
  }

  // Test getBookByAuthorSubstr method
  @Test
  void testGetBookByAuthorSubstr() {
    String searchKeyword = "Smith";
    ArrayList<Book> books = Filter.getInstance().getBookByAuthorSubstr(searchKeyword);

    //(books, "The result should not be null.");
    //assertTrue(books.size() > 0, "The result should return at least one book if matching entries exist.");

    // Check if all returned books have the author name containing the search keyword
    for (Book book : books) {
      assertTrue(book.getAuthors().toLowerCase().contains(searchKeyword.toLowerCase()),
          "Book author should contain the search keyword.");
    }
  }

  // Test getBookByCategorySubstr method
  @Test
  void testGetBookByCategorySubstr() {
    String searchKeyword = "Science";
    ArrayList<Book> books = Filter.getInstance().getBookByCategorySubstr(searchKeyword);

    assertNotNull(books, "The result should not be null.");
    assertTrue(books.size() > 0, "The result should return at least one book if matching entries exist.");

    // Check if all returned books have the category containing the search keyword
    for (Book book : books) {
      assertTrue(book.getCategory().toLowerCase().contains(searchKeyword.toLowerCase()),
          "Book category should contain the search keyword.");
    }
  }

  // Test getUserBySubstr method
  @Test
  void testGetUserBySubstr() {
    String searchKeyword = "John";
    String userType = "ALL";
    ArrayList<User> users = Filter.getInstance().getUserBySubstr(searchKeyword, userType);

    //assertNotNull(users, "The result should not be null.");
    //assertTrue(users.size() > 0, "The result should return at least one user if matching entries exist.");

    // Check if all returned users have the name containing the search keyword
    for (User user : users) {
      assertTrue(user.getName().toLowerCase().contains(searchKeyword.toLowerCase()),
          "User name should contain the search keyword.");
    }
  }

  // Test getUserList method
  @Test
  void testGetUserList() {
    String userType = "ALL";
    ArrayList<User> users = Filter.getUserList(userType);

    assertNotNull(users, "The result should not be null.");
    assertTrue(users.size() > 0, "The result should return at least one user if entries exist.");

    // If userType is not "ALL", check if the returned users match the specified type
    if (!userType.equalsIgnoreCase("ALL")) {
      for (User user : users) {
        assertEquals(userType, user.getUserType(),
            "The user's type should match the specified type.");
      }
    }
  }
}