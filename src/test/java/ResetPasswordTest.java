
import org.junit.jupiter.api.Test;
import src.librarysystem.EmailSender;

import static org.junit.jupiter.api.Assertions.*;
public class ResetPasswordTest {
    @Test
    public void testSendEmail() {
        EmailSender emailSender = new EmailSender();
        String toEmail = "123@gmail.com";
        String subject = "Test Email";
        String body = "This is a test email.";
        assert(emailSender.sendEmail(toEmail, subject, body));
    }

    @Test
    public void testUpdatePassword() {

    }
}
