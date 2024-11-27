package src.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Dịch vụ phục hồi mật khẩu cho người dùng. Cung cấp các phương thức để tạo mã xác nhận,
 * lưu trữ token vào cơ sở dữ liệu, kiểm tra tính hợp lệ của token và gửi mã xác nhận qua email.
 */
public class PasswordRecoveryService {

  /**
   * Tạo mã xác nhận ngẫu nhiên gồm 4 chữ số.
   *
   * @return Mã xác nhận ngẫu nhiên.
   */
  private static String randomCode() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      int digit = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
      sb.append(digit);
    }
    return sb.toString();
  }

  /**
   * Lưu token phục hồi mật khẩu vào cơ sở dữ liệu.
   *
   * @param username Tên người dùng yêu cầu phục hồi mật khẩu.
   * @param token Mã token phục hồi mật khẩu.
   * @param expiryDate Thời gian hết hạn của token.
   * @throws RuntimeException Nếu không thể lưu token vào cơ sở dữ liệu.
   */
  public static void saveResetToken(String username, String token, LocalDateTime expiryDate) {
    String query = "INSERT INTO password_reset_token (token, username, expiry_date) VALUES(?,?,?)";
    try (Connection connection = DBInfo.conn();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, token);
      statement.setTimestamp(3, Timestamp.valueOf(expiryDate));
      statement.setString(2, username);
      int rows = statement.executeUpdate();
      if (rows > 0) {
        System.out.println("Token được lưu thành công.");
      } else {
        throw new IllegalArgumentException("Username không tồn tại.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Không thể lưu token vào cơ sở dữ liệu.");
    }
  }

  /**
   * Kiểm tra tính hợp lệ của token phục hồi mật khẩu.
   *
   * @param username Tên người dùng yêu cầu phục hồi mật khẩu.
   * @param token Mã token cần kiểm tra.
   * @return true nếu token hợp lệ và chưa hết hạn, ngược lại trả về false.
   * @throws RuntimeException Nếu xảy ra lỗi khi kiểm tra token.
   */
  public static boolean validateResetToken(String username, String token) {
    String query = "SELECT expiry_date FROM password_reset_token " +
        "WHERE token = ? AND username = ?";
    try (Connection connection = DBInfo.conn();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, token);
      statement.setString(2, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        LocalDateTime expiryDate = resultSet.getTimestamp("expiry_date").toLocalDateTime();
        return expiryDate.isAfter(LocalDateTime.now()); // Token hợp lệ nếu chưa hết hạn
      }
      return false; // Token không hợp lệ hoặc đã hết hạn
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Lỗi khi kiểm tra token.");
    }
  }

  /**
   * Gửi mã xác nhận phục hồi mật khẩu đến email của người dùng.
   *
   * @param username Tên người dùng yêu cầu phục hồi mật khẩu.
   * @return true nếu gửi email thành công, false nếu có lỗi xảy ra.
   */
  public static boolean sendToken(String username) {
    String token = randomCode();
    System.out.println(token); // In mã token ra console (có thể thay bằng logging thực tế)
    if (EmailSender.sendEmail(username, "Mã xác nhận là: ", token)) {
      LocalDateTime expire = LocalDateTime.now().plusMinutes(40);
      saveResetToken(username, token, expire); // Lưu token vào cơ sở dữ liệu
      return true;
    }
    return false;
  }

  /**
   * Kiểm tra token phục hồi mật khẩu của người dùng.
   *
   * @param username Tên người dùng yêu cầu phục hồi mật khẩu.
   * @param token Mã token cần kiểm tra.
   * @return true nếu token hợp lệ, false nếu không hợp lệ.
   */
  public static boolean checkToken(String username, String token) {
    return validateResetToken(username, token);
  }
}
