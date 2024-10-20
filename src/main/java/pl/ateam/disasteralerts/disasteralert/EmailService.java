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
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class EmailService implements AlertListener{

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {

        interestedUsers.forEach(interestedUser -> {
            sendEmail(interestedUser.email(),"Alert", alertAddDTO.description());
        });

    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
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
