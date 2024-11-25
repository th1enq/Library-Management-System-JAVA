package src.librarysystem;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

  /**
   * Phương thức mã hóa mật khẩu.
   *
   * @param plainPassword Mật khẩu gốc.
   * @return Chuỗi mật khẩu đã mã hóa.
   */
  public static String hashPassword(String plainPassword) {
    int saltRounds = 10;
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt(saltRounds));
  }

  /**
   * Phương thức kiểm tra mật khẩu khi đăng nhập.
   *
   * @param plainPassword Mật khẩu gốc do người dùng nhập.
   * @param hashedPassword Mật khẩu đã mã hóa lưu trong cơ sở dữ liệu.
   * @return true nếu mật khẩu hợp lệ, ngược lại false.
   */
  public static boolean verifyPassword(String plainPassword, String hashedPassword) {
    try {
      return BCrypt.checkpw(plainPassword, hashedPassword);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] args) {
    String originalPassword = "";
    String hashedPassword = hashPassword(originalPassword);
    System.out.println("Mật khẩu đã mã hóa: " + hashedPassword);
    boolean isMatch = verifyPassword("my_password", hashedPassword);
    System.out.println("Kết quả kiểm tra mật khẩu: " + (isMatch ? "Hợp lệ" : "Không hợp lệ"));
  }
}
