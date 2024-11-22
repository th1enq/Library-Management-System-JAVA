package src.librarysystem;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;

public class User {

  private int id;
  private String name;
  private String username;
  private String password;
  private String userType;
  private boolean isBanned;
  private String avatarLink;
  private String MSV;
  private String university;
  private String phone;
  private String coverPhotoLink;
  private int reputation;
  private ArrayList<Pair<Book, MyDateTime>> rentBook;


  public User() {
    rentBook = new ArrayList<>();
  }

  public User(int id, String name, String username, String password, String userType,
      boolean isBanned, String avatarLink) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.password = password;
    this.userType = userType;
    this.isBanned = isBanned;
    this.avatarLink = avatarLink;
    rentBook = DBInfo.getBorrowedBookList(this.id);
  }

  public User(int id, String name, String username, String password, String userType) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.password = password;
    this.userType = userType;
    this.isBanned = false;
    this.avatarLink = null;
    rentBook = DBInfo.getBorrowedBookList(this.id);

  }

  public User(int id, String name, String username, String password, String userType,
      boolean isBanned, String avatarLink, String MSV, String university,
      String phone, String coverPhotoLink, int reputation) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.password = password;
    this.userType = userType;
    this.isBanned = isBanned;
    this.avatarLink = avatarLink;
    this.MSV = MSV;
    this.university = university;
    this.phone = phone;
    this.coverPhotoLink = coverPhotoLink;
    this.reputation = reputation;
    rentBook = DBInfo.getBorrowedBookList(this.id);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
    rentBook = DBInfo.getBorrowedBookList(this.id);

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setBanned(boolean banned) {
    isBanned = banned;
  }

  public String getAvatarLink() {
    return avatarLink;
  }

  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }

  public String getMSV() {
    return MSV;
  }

  public void setMSV(String MSV) {
    this.MSV = MSV;
  }

  public String getUniversity() {
    return university;
  }

  public void setUniversity(String university) {
    this.university = university;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCoverPhotoLink() {
    return coverPhotoLink;
  }

  public void setCoverPhotoLink(String coverPhotoLink) {
    this.coverPhotoLink = coverPhotoLink;
  }

  public int getReputation() {
    return reputation;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
  }



  public ArrayList<Notification> getNotifications() {
    return  DBInfo.getNotificationsByUserId(id);
  }

  public ArrayList<Pair<Book, MyDateTime>> getRentBook() {
    return rentBook;
  }

  public void setRentBook(
      ArrayList<Pair<Book, MyDateTime>> rentBook) {
    this.rentBook = rentBook;
  }

  public int getOverdue() {
    return DBInfo.getOverdueAndUpcoming(id).getValue();
  }

  public int getUpcoming() {
    return DBInfo.getOverdueAndUpcoming(id).getKey();
  }

  @Override
  public String toString() {
    return "Registration{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", userType='" + userType + '\'' +
        ", isBanned=" + isBanned +
        ", avatarLink='" + avatarLink + '\'' +
        ", MSV='" + MSV + '\'' +
        ", university='" + university + '\'' +
        ", phone='" + phone + '\'' +
        ", coverPhotoLink='" + coverPhotoLink + '\'' +
        ", reputation=" + reputation +
        '}';
  }


  public ObservableValue<String> getStatus() {
    return null;
  }

  public void muonSach(Book book) {
    DBInfo.borrowBook(book.getTitle(), id);
    rentBook = DBInfo.getBorrowedBookList(id);
  }

  public void traSach(Book book) throws Exception {
    DBInfo.returnBook(book.getTitle(), id);
    rentBook = DBInfo.getBorrowedBookList(id);
  }

  public boolean checkSach(Book book) {
    return rentBook.contains(book);
  }

  public void update(String newName, String newUsername, String newPassword,
      String newAvatar_link) {
    DBInfo.updateUser(id, newName, newUsername, newPassword, newAvatar_link);
    if (newName != null) {
      setName(newName);
    }
    if (newUsername != null) {
      setUsername(newUsername);
    }
    if (newPassword != null) {
      setPassword(newPassword);
    }
    if (newAvatar_link != null) {
      setAvatarLink(newAvatar_link);
    }
  }

  public void update(String newName, String newUsername, String newPassword,
      String newAvatarLink, String newMSV, String newUniversity,
      String newPhone, String newCoverPhotoLink, Integer newReputation) {

    DBInfo.updateUser(id, newName, newUsername, newPassword, newAvatarLink,
        newMSV, newUniversity, newPhone, newCoverPhotoLink, newReputation);

    if (newName != null) {
      setName(newName);
    }
    if (newUsername != null) {
      setUsername(newUsername);
    }
    if (newPassword != null) {
      setPassword(newPassword);
    }
    if (newAvatarLink != null) {
      setAvatarLink(newAvatarLink);
    }
    if (newMSV != null) {
      setMSV(newMSV);
    }
    if (newUniversity != null) {
      setUniversity(newUniversity);
    }
    if (newPhone != null) {
      setPhone(newPhone);
    }
    if (newCoverPhotoLink != null) {
      setCoverPhotoLink(newCoverPhotoLink);
    }
    if (newReputation != null) {
      setReputation(newReputation);
    }
  }

  public void acceptBorrowRequest(BorrowRequest a) {
    if (this.userType.equals("admin")) {
      DBInfo.acceptBorrowRequest(a);
    }
  }

  public ArrayList<BorrowRequest> getBorrowRequest() {
    return DBInfo.getBorrowRequest();
  }
  public void deleteNotifications() {
    DBInfo.deleteNotificationsByUserId(id);
  }

  public void sendNotification(int receiver_id,String tmp) {
    DBInfo.sendNotification(this.id, receiver_id, tmp);

  }

  public void sendNotification(User receiver,String tmp) {
    DBInfo.sendNotification(this.id, receiver.getId(), tmp);

  }

  public void reply(Notification A, String tmp) {
    DBInfo.sendNotification(this.id, A.getSenderId(), tmp);

  }
  

}