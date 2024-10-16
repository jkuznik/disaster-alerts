package pl.ateam.disasteralerts.disasteralert;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void shouldSendEmailSuccessfully() throws Exception {
        // given
        String recipient = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        // when
        notificationService.sendEmail(recipient, subject, content);

        // then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsEmpty() {
        // given
        String recipient = "";
        String subject = "Test Subject";
        String content = "Test Content";

        // when & then
        assertThatThrownBy(() -> notificationService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with creating the message");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsNull() {
        // given
        String recipient = null;
        String subject = "Test Subject";
        String content = "Test Content";

        // when & then
        assertThatThrownBy(() -> notificationService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with creating the message");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void shouldSendEmailWithEmptyContent() throws MessagingException {
        // given
        String recipient = "test@example.com";
        String subject = "Test Subject";
        String content = "";  // Pusta treść

        // when
        notificationService.sendEmail(recipient, subject, content);

        // then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenMimeMessageCreationFails() throws Exception {
        // given
        String recipient = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        doThrow(MessagingException.class).when(mailSender).createMimeMessage();

        // when & then
        assertThatThrownBy(() -> notificationService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with creating the message");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailSendingFails() throws Exception {
        // given
        String recipient = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        doThrow(MailException.class).when(mailSender).send(any(MimeMessage.class));

        // when & then
        assertThatThrownBy(() -> notificationService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with sending the message");

        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsNull() {
        // given
        String recipient = "test@example.com";
        String subject = null;  // Nullowy temat
        String content = "Test Content";

        // when & then
        assertThatThrownBy(() -> notificationService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with creating the message");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }
}
