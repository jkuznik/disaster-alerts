package pl.ateam.disasteralerts.notification;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    private String recipient;
    private String subject;
    private String content;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        recipient = "test@gmail.com";
        subject = "Disaster Alert";
        content = "Disaster Alert";
    }

    @Test
    void shouldSendEmailSuccessfully() {
        // given

        // when
        emailService.sendEmail(recipient, subject, content);

        // then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsEmpty() {
        // given
        String recipient = "";

        // when & then
        assertThatThrownBy(() -> emailService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with creating the message");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsNull() {
        // given
        String recipient = null;

        // when & then
        assertThatThrownBy(() -> emailService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("To address must not be null");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void shouldSendEmailWithEmptyContent() {
        // given
        String content = "";

        // when
        emailService.sendEmail(recipient, subject, content);

        // then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenMimeMessageCreationFails() {
        // given
        doThrow(new IllegalStateException("Mocked exception")).when(mailSender).createMimeMessage();

        // when & then
        assertThatThrownBy(() -> emailService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Mocked exception");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailSendingFails() {
        // given
        doThrow(MailSendException.class).when(mailSender).send(any(MimeMessage.class));

        // when & then
        assertThatThrownBy(() -> emailService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Problem with sending the message");

        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsNull() {
        // given
        String subject = null;

        // when & then
        assertThatThrownBy(() -> emailService.sendEmail(recipient, subject, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Subject must not be null");

        verify(mailSender, never()).send(any(MimeMessage.class));
    }
}
