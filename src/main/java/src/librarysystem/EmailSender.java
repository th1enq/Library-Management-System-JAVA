package src.librarysystem;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp này cung cấp chức năng gửi email sử dụng giao thức SMTP của Gmail.
 * Phương thức `sendEmail` gửi email đến người nhận với chủ đề và nội dung được chỉ định.
 */
public class EmailSender {

  /**
   * Gửi email đến người nhận với chủ đề và nội dung đã cho.
   *
   * @param recipient Địa chỉ email của người nhận.
   * @param subject Chủ đề của email.
   * @param content Nội dung của email.
   * @return Trả về `true` nếu gửi email thành công, `false` nếu thất bại.
   */
  public static boolean sendEmail(String recipient, String subject, String content) {
    boolean ok = true;

    // Cấu hình các thuộc tính SMTP
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com"); // Máy chủ SMTP
    properties.put("mail.smtp.port", "587"); // Cổng SMTP
    properties.put("mail.smtp.auth", "true"); // Bật xác thực
    properties.put("mail.smtp.starttls.enable", "true"); // Bật STARTTLS

    // Thông tin đăng nhập
    String email = "json00906@gmail.com"; // Email của bạn
    String appPassword = "ofnm kjvt vgen vszq"; // Mật khẩu ứng dụng

    // Tạo phiên làm việc với xác thực
    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(email, appPassword);
      }
    });

    try {
      // Tạo nội dung email
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(email)); // Địa chỉ người gửi
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); // Người nhận
      message.setSubject(subject); // Chủ đề email
      message.setText(content); // Nội dung email

      // Gửi email
      Transport.send(message);
      System.out.println("Email sent successfully to " + recipient);
      ok = true;
    } catch (MessagingException e) {
      e.printStackTrace();
      System.err.println("Failed to send email: " + e.getMessage());
      ok = false;
    }
    return ok;
  }

  /**
   * Phương thức chính dùng để kiểm tra việc gửi email.
   * Gửi một email mẫu đến một địa chỉ email cụ thể với chủ đề và nội dung định trước.
   *
   * @param args Tham số dòng lệnh (không sử dụng trong phương thức này).
   */
  public static void main(String[] args) {
    // Test gửi email
    sendEmail("23020135@vnu.edu.vn", "Email 7", "Khong nen vuot muc pickleball");
  }
}
