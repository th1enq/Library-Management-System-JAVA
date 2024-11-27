package src.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class PasswordRecoveryService {

  private static String randomCode() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      int digit = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
      sb.append(digit);
    }
    String randomString = sb.toString();
    return randomString;
  }


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
        if (expiryDate.isAfter(LocalDateTime.now())) {
          return true; // Token hợp lệ
        }
      }
      return false; // Token không hợp lệ hoặc đã hết hạn
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Lỗi khi kiểm tra token.");
    }
  }


  public static boolean sendToken(String username) {
    ;
    String token = randomCode();
    System.out.println(token);
    if (EmailSender.sendEmail(username, "Mã xác nhận là: ", token)) {
      LocalDateTime expire = LocalDateTime.now().plusMinutes(40);
      saveResetToken(username, token, expire);
      return true;
    }
    return false;
  }

  public static boolean checkToken(String username, String token) {
    return validateResetToken(username, token);
  }
}