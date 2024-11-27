package src.librarysystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;

/**
 * Lớp đại diện cho người dùng trong hệ thống. Lớp này chứa thông tin chi tiết của người dùng như
 * tên, username, mật khẩu, avatar, các thông tin cá nhân, thông báo, và các chức năng cho phép
 * người dùng tương tác với sách trong hệ thống, bao gồm mượn sách, trả sách, nhận và gửi thông
 * báo.
 */
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

  /**
   * Khởi tạo người dùng với một danh sách sách đã mượn rỗng.
   */
  public User() {
    rentBook = new ArrayList<>();
  }

  /**
   * Khởi tạo người dùng với thông tin cơ bản và danh sách sách đã mượn.
   *
   * @param id         ID của người dùng.
   * @param name       Tên của người dùng.
   * @param username   Tên đăng nhập của người dùng.
   * @param password   Mật khẩu của người dùng.
   * @param userType   Loại người dùng (ví dụ: admin, user).
   * @param isBanned   Trạng thái cấm tài khoản (true nếu bị cấm, false nếu không).
   * @param avatarLink Đường dẫn đến ảnh đại diện của người dùng.
   */
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

  /**
   * Khởi tạo người dùng với thông tin cơ bản và danh sách sách đã mượn.
   *
   * @param id       ID của người dùng.
   * @param name     Tên của người dùng.
   * @param username Tên đăng nhập của người dùng.
   * @param password Mật khẩu của người dùng.
   * @param userType Loại người dùng (ví dụ: admin, user).
   */
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

  /**
   * Khởi tạo người dùng với thông tin đầy đủ và danh sách sách đã mượn.
   *
   * @param id             ID của người dùng.
   * @param name           Tên của người dùng.
   * @param username       Tên đăng nhập của người dùng.
   * @param password       Mật khẩu của người dùng.
   * @param userType       Loại người dùng (ví dụ: admin, user).
   * @param isBanned       Trạng thái cấm tài khoản (true nếu bị cấm, false nếu không).
   * @param avatarLink     Đường dẫn đến ảnh đại diện của người dùng.
   * @param MSV            Mã số sinh viên của người dùng (nếu có).
   * @param university     Tên trường đại học của người dùng (nếu có).
   * @param phone          Số điện thoại của người dùng.
   * @param coverPhotoLink Đường dẫn đến ảnh bìa của người dùng (nếu có).
   * @param reputation     Điểm uy tín của người dùng.
   */
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

  /**
   * Lấy ID của người dùng.
   *
   * @return ID của người dùng.
   */
  public int getId() {
    return id;
  }

  /**
   * Cập nhật ID của người dùng.
   *
   * @param id ID mới của người dùng.
   */
  public void setId(int id) {
    this.id = id;
    rentBook = DBInfo.getBorrowedBookList(this.id);
  }

  /**
   * Lấy tên của người dùng.
   *
   * @return Tên người dùng.
   */
  public String getName() {
    return name;
  }

  /**
   * Cập nhật tên của người dùng.
   *
   * @param name Tên mới của người dùng.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Lấy tên đăng nhập của người dùng.
   *
   * @return Tên đăng nhập của người dùng.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Cập nhật tên đăng nhập của người dùng.
   *
   * @param username Tên đăng nhập mới của người dùng.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Lấy mật khẩu của người dùng.
   *
   * @return Mật khẩu của người dùng.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Cập nhật mật khẩu của người dùng.
   *
   * @param password Mật khẩu mới của người dùng.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Lấy loại người dùng (admin, user, v.v).
   *
   * @return Loại người dùng.
   */
  public String getUserType() {
    return userType;
  }

  /**
   * Cập nhật loại người dùng.
   *
   * @param userType Loại người dùng mới.
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

  /**
   * Kiểm tra trạng thái cấm tài khoản của người dùng.
   *
   * @return true nếu người dùng bị cấm, false nếu không.
   */
  public boolean isBanned() {
    return isBanned;
  }

  /**
   * Cập nhật trạng thái cấm tài khoản của người dùng.
   *
   * @param banned Trạng thái cấm tài khoản mới.
   */
  public void setBanned(boolean banned) {
    isBanned = banned;
  }

  /**
   * Lấy đường dẫn đến ảnh đại diện của người dùng.
   *
   * @return Đường dẫn đến ảnh đại diện của người dùng.
   */
  public String getAvatarLink() {
    return avatarLink;
  }

  /**
   * Cập nhật đường dẫn đến ảnh đại diện của người dùng.
   *
   * @param avatarLink Đường dẫn đến ảnh đại diện mới.
   */
  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }

  /**
   * Lấy mã số sinh viên của người dùng.
   *
   * @return Mã số sinh viên của người dùng.
   */
  public String getMSV() {
    return MSV;
  }

  /**
   * Cập nhật mã số sinh viên của người dùng.
   *
   * @param MSV Mã số sinh viên mới.
   */
  public void setMSV(String MSV) {
    this.MSV = MSV;
  }

  /**
   * Lấy tên trường đại học của người dùng.
   *
   * @return Tên trường đại học của người dùng.
   */
  public String getUniversity() {
    return university;
  }

  /**
   * Cập nhật tên trường đại học của người dùng.
   *
   * @param university Tên trường đại học mới.
   */
  public void setUniversity(String university) {
    this.university = university;
  }

  /**
   * Lấy số điện thoại của người dùng.
   *
   * @return Số điện thoại của người dùng.
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Cập nhật số điện thoại của người dùng.
   *
   * @param phone Số điện thoại mới của người dùng.
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Lấy đường dẫn đến ảnh bìa của người dùng.
   *
   * @return Đường dẫn đến ảnh bìa của người dùng.
   */
  public String getCoverPhotoLink() {
    return coverPhotoLink;
  }

  /**
   * Cập nhật đường dẫn đến ảnh bìa của người dùng.
   *
   * @param coverPhotoLink Đường dẫn đến ảnh bìa mới.
   */
  public void setCoverPhotoLink(String coverPhotoLink) {
    this.coverPhotoLink = coverPhotoLink;
  }

  /**
   * Lấy điểm uy tín của người dùng.
   *
   * @return Điểm uy tín của người dùng.
   */
  public int getReputation() {
    return reputation;
  }

  /**
   * Cập nhật điểm uy tín của người dùng.
   *
   * @param reputation Điểm uy tín mới của người dùng.
   */
  public void setReputation(int reputation) {
    this.reputation = reputation;
  }

  /**
   * Lấy danh sách thông báo của người dùng từ cơ sở dữ liệu.
   *
   * @return Danh sách thông báo của người dùng.
   */
  public ArrayList<Notification> getNotifications() {
    return DBInfo.getNotificationsByUserId(id);
  }

  /**
   * Lấy danh sách sách mà người dùng đã mượn.
   *
   * @return Danh sách sách đã mượn.
   */
  public ArrayList<Pair<Book, MyDateTime>> getRentBook() {
    return rentBook;
  }

  /**
   * Cập nhật danh sách sách đã mượn của người dùng.
   *
   * @param rentBook Danh sách sách mới.
   */
  public void setRentBook(ArrayList<Pair<Book, MyDateTime>> rentBook) {
    this.rentBook = rentBook;
  }

  /**
   * Lấy số lượng sách quá hạn mà người dùng đã mượn.
   *
   * @return Số lượng sách quá hạn.
   */
  public int getOverdue() {
    return DBInfo.getOverdueAndUpcoming(id).getValue();
  }

  /**
   * Lấy số lượng sách sắp đến hạn trả mà người dùng đã mượn.
   *
   * @return Số lượng sách sắp đến hạn trả.
   */
  public int getUpcoming() {
    return DBInfo.getOverdueAndUpcoming(id).getKey();
  }

  /**
   * Trả về chuỗi mô tả đối tượng người dùng.
   *
   * @return Chuỗi mô tả người dùng.
   */
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

  /**
   * Trả về trạng thái của người dùng dưới dạng một giá trị Observable (dành cho giao diện người
   * dùng).
   *
   * @return Trạng thái của người dùng.
   */
  public ObservableValue<String> getStatus() {
    return null;
  }

  /**
   * Mượn một cuốn sách.
   *
   * @param book Cuốn sách được mượn.
   */
  public void muonSach(Book book) {
    DBInfo.borrowBook(book.getTitle(), id);
    rentBook = DBInfo.getBorrowedBookList(id);
  }

  /**
   * Trả một cuốn sách.
   *
   * @param book Cuốn sách được trả.
   * @throws Exception Nếu có lỗi xảy ra khi trả sách.
   */
  public void traSach(Book book) throws Exception {
    DBInfo.returnBook(book.getTitle(), id);
    rentBook = DBInfo.getBorrowedBookList(id);
  }

  /**
   * Kiểm tra xem người dùng đã mượn cuốn sách hay chưa.
   *
   * @param book Cuốn sách cần kiểm tra.
   * @return true nếu người dùng đã mượn sách, false nếu không.
   */
  public boolean checkSach(Book book) {
    return rentBook.contains(book);
  }

  /**
   * Cập nhật thông tin cá nhân của người dùng.
   *
   * @param newName        Tên mới.
   * @param newUsername    Tên đăng nhập mới.
   * @param newPassword    Mật khẩu mới.
   * @param newAvatar_link Đường dẫn ảnh đại diện mới.
   */
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

  /**
   * Cập nhật thông tin chi tiết của người dùng.
   *
   * @param newName           Tên mới.
   * @param newUsername       Tên đăng nhập mới.
   * @param newPassword       Mật khẩu mới.
   * @param newAvatarLink     Đường dẫn ảnh đại diện mới.
   * @param newMSV            Mã số sinh viên mới.
   * @param newUniversity     Tên trường đại học mới.
   * @param newPhone          Số điện thoại mới.
   * @param newCoverPhotoLink Đường dẫn ảnh bìa mới.
   * @param newReputation     Điểm uy tín mới.
   */
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

  /**
   * Chấp nhận yêu cầu mượn sách của người dùng.
   *
   * @param a Yêu cầu mượn sách.
   */
  public void acceptBorrowRequest(BookIssue a) {
    if (this.userType.equals("admin")) {
      DBInfo.acceptBorrowRequest(a.getUserId(), a.getBookTitle());
    }
  }

  /**
   * Từ chối yêu cầu mượn sách của người dùng.
   *
   * @param a Yêu cầu mượn sách.
   */
  public void denyBorrowRequest(BookIssue a) {
    if (this.userType.equals("admin")) {
      DBInfo.denyBorrowRequest(a.getUserId(), a.getBookTitle());
    }
  }

  /**
   * Xóa tất cả thông báo của người dùng.
   */
  public void deleteNotifications() {
    DBInfo.deleteNotificationsByUserId(id);
  }

  /**
   * Xóa một thông báo cụ thể của người dùng.
   *
   * @param i Thông báo cần xóa.
   */
  public void deleteOneNotification(Notification i) {
    DBInfo.deleteOneNotification(i);
  }

  /**
   * Gửi thông báo đến người dùng khác.
   *
   * @param receiver_id ID của người nhận.
   * @param tmp         Nội dung thông báo.
   */
  public void sendNotification(int receiver_id, String tmp, int type) {
    DBInfo.sendNotification(this.id, receiver_id, tmp, type);
  }

  /**
   * Gửi thông báo cho người dùng khác.
   *
   * @param receiver Người nhận thông báo.
   * @param tmp      Nội dung thông báo.
   */
  public void sendNotification(User receiver, String tmp,int type) {
    DBInfo.sendNotification(this.id, receiver.getId(), tmp,type);
  }

  /**
   * Cấm một người dùng khác.
   *
   * @param X Người dùng bị cấm.
   */
  public void ban(User X) {
    if (userType.equals("admin")) {
      DBInfo.ban(X);
      X.setBanned(true);
    }
  }

  /**
   * Gỡ bỏ cấm đối với một người dùng khác.
   *
   * @param X Người dùng được gỡ bỏ cấm.
   */
  public void unBan(User X) {
    if (userType.equals("admin")) {
      DBInfo.unBan(X);
      X.setBanned(false);
    }
  }

  /**
   * Thêm bình luận về một cuốn sách.
   *
   * @param book    Cuốn sách cần bình luận.
   * @param content Nội dung bình luận.
   * @param rate    Điểm đánh giá.
   */
  public void addComment(Book book, String content, int rate) {
    DBInfo.addComment(
        new Comment(book.getTitle(), getUsername(), new MyDateTime(LocalDateTime.now()), content,
            rate));
  }
}