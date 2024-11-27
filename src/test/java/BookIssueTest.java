import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import src.librarysystem.BookIssue;
import src.librarysystem.BookIssueDB;
import src.librarysystem.MyDateTime;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BookIssueTest {
  private static BookIssue testBookIssue;
  private static final int TEST_USER_ID = 1;



  @Test
  void testGetTotalList() {
    ArrayList<BookIssue> totalList = BookIssueDB.getTotalList();
    assertNotNull(totalList);
  }

  @Test
  void testGetBorrowedList() {
    ArrayList<BookIssue> borrowedList = BookIssueDB.getBorrowedList();
    assertNotNull(borrowedList);

    // Verify all items in borrowed list have correct status
    for (BookIssue issue : borrowedList) {
      assertNotEquals(BookIssue.STATUS_RETURNED, issue.getStatus());
      assertNotEquals(BookIssue.STATUS_PENDING, issue.getStatus());
    }
  }

  @Test
  void testGetReturnedList() {
    ArrayList<BookIssue> returnedList = BookIssueDB.getReturnedList();
    assertNotNull(returnedList);

    // Verify all items in returned list have RETURNED status
    for (BookIssue issue : returnedList) {
      assertEquals(BookIssue.STATUS_RETURNED, issue.getStatus());
    }
  }

  @Test
  void testGetDelayList() {
    ArrayList<BookIssue> delayList = BookIssueDB.getDelayList();
    assertNotNull(delayList);

    // Verify all items in delay list have DELAY status
    for (BookIssue issue : delayList) {
      assertEquals(BookIssue.STATUS_DELAY, issue.getStatus());
    }
  }

  @Test
  void testGetPendingList() {
    ArrayList<BookIssue> pendingList = BookIssueDB.getPendingList();
    assertNotNull(pendingList);

    // Verify all items in pending list have PENDING status
    for (BookIssue issue : pendingList) {
      assertEquals(BookIssue.STATUS_PENDING, issue.getStatus());
    }
  }

  @Test
  void testGetTotalListByUserId() {
    ArrayList<BookIssue> userList = BookIssueDB.getTotalListByUserId(TEST_USER_ID);
    assertNotNull(userList);

    // Verify all items belong to the specified user
    for (BookIssue issue : userList) {
      assertEquals(TEST_USER_ID, issue.getUserId());
    }
  }

  @Test
  void testGetBorrowedListByUserId() {
    ArrayList<BookIssue> borrowedList = BookIssueDB.getBorrowedListByUserId(TEST_USER_ID);
    assertNotNull(borrowedList);

    // Verify all items are borrowed by the specified user
    for (BookIssue issue : borrowedList) {
      assertEquals(TEST_USER_ID, issue.getUserId());
      assertNotEquals(BookIssue.STATUS_RETURNED, issue.getStatus());
      assertNotEquals(BookIssue.STATUS_PENDING, issue.getStatus());
    }
  }

  @Test
  void testGetReturnedListByUserId() {
    ArrayList<BookIssue> returnedList = BookIssueDB.getReturnedListByUserId(TEST_USER_ID);
    assertNotNull(returnedList);

    // Verify all items are returned by the specified user
    for (BookIssue issue : returnedList) {
      assertEquals(TEST_USER_ID, issue.getUserId());
      assertEquals(BookIssue.STATUS_RETURNED, issue.getStatus());
    }
  }

  @Test
  void testGetPendingListByUserId() {
    ArrayList<BookIssue> pendingList = BookIssueDB.getPendingListByUserId(TEST_USER_ID);
    assertNotNull(pendingList);

    // Verify all items are pending for the specified user
    for (BookIssue issue : pendingList) {
      assertEquals(TEST_USER_ID, issue.getUserId());
      assertEquals(BookIssue.STATUS_PENDING, issue.getStatus());
    }
  }
}