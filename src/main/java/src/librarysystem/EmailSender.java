package src.librarysystem;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSender {

  //(mail nguoi nhan, chu de mail, noi dung mail)
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

  public static void main(String[] args) {
    // Test gửi email

    //(mail nguoi nhan, chu de mail, noi dung mail)
    sendEmail("23020135@vnu.edu.vn", "Email 7",
        "Khong nen vuot muc pickleball");
  }
}
