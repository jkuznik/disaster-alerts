package pl.ateam.disasteralerts.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringJUnitConfig
class TwilioClientTest {

    @Value("${twilio.test.account.sid}")
    private String testAccountSid;

    @Value("${twilio.test.auth.token}")
    private String testAuthToken;

    private static final String FROM = "+15005550006"; // this is a twilio dedicated number for tests

    @BeforeEach
    void setUp() {
        Twilio.init(testAccountSid, testAuthToken);
    }

    @Test
    void shouldSendSMS_whenTwilioInitIsSuccess() {
        //given
        var validMessage = "Test SMS message";
        var to = "+48123456789";

        //then
        assertThatNoException().isThrownBy(() -> Message
                .creator(
                        new PhoneNumber(to),
                        new PhoneNumber(FROM),
                        validMessage
                )
                .create());
    }
}