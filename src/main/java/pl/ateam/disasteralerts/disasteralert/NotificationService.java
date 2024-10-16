package pl.ateam.disasteralerts.disasteralert;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private JavaMailSender mailSender;

    void sendEmail(String sender, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(sender);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Error creating email message: {}", e.getMessage());
            throw new RuntimeException("Problem with creating the message", e);
        } catch (MailException e) {
            logger.error("Error sending email message: {}", e.getMessage());
            throw new RuntimeException("Problem with sending the message", e);
        } catch (Exception e) {
            logger.error("Unknown error while sending email: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
