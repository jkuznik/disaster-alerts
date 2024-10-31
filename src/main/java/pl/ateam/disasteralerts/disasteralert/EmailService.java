package pl.ateam.disasteralerts.disasteralert;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.smtp.SMTPSenderFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
class EmailService implements AlertListener {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        interestedUsers.forEach(interestedUser -> {
            executor.submit(() -> {
                try {
                    sendEmailWithRetry(interestedUser.email(), "Alert for " + alertAddDTO.location(), alertAddDTO.description());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });

        executor.shutdown();
    }

    @Retryable(
            retryFor = SMTPSenderFailedException.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 3000)
    )
    public void sendEmailWithRetry(String to, String subject, String body){
        sendEmail(to, subject, body);
    }

    @Recover
    public String handleMessagingException(MessagingException e) {
        logger.error("Max attempts reached. Failed to send email after 4 attempts.");
        logger.error("Error message: {}", e.getMessage());

        return "Max attempts reached. Failed to send email";
    }

    public void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);

            logger.info("Email sent to " + recipient + " with subject " + subject + " and content " + content);

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
