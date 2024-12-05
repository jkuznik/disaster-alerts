package pl.ateam.disasteralerts.notification;

import nl.altindag.log.LogCaptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {SMSService.class, SMSLimitService.class, SMSLimitRepository.class, NotificationManager.class, TwilioClient.class})
class SMSServiceTest {

    @Autowired
    SMSService smsService;

    @Autowired
    SMSLimitService smsLimitService;

    @MockBean
    TwilioClient twilioClient;

    @MockBean
    NotificationManager notificationManager;

    @MockBean
    SMSLimitService smsLimitServiceMock;

    @MockBean
    SMSLimitRepository smsLimitRepository;

    private final String alertDescription = "Test description";
    private final String validPhoneNumber = "+48123456789";

    @Nested
    class SMSServiceTests {

        private final String tooLongDescription = """ 
                                      Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                                      Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                                      Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris 
                                      nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in 
                                      reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla 
                                      pariatur. Excepteur sint occaecat cupidatat non proident, sunt in 
                                      culpa qui officia deserunt mollit anim id est laborum.
                                        """;
        private final String notValidPhoneNumber = "+481234567890";

        @Test
        void shouldSendSMSAndIncreaseSMSDayLimitCounter_whenMessageAndPhoneNumberAreValid() {
            //when
            when(smsLimitServiceMock.isBelowLimit(any(LocalDateTime.class))).thenReturn(true);
            doNothing().when(twilioClient).sendSMS(alertDescription, validPhoneNumber);

            //then
            Assertions.assertThatNoException().isThrownBy(() -> smsService.sendSMS(alertDescription, validPhoneNumber));
        }

        @Test
        void shouldTriggeredLogger_whenSMSLimitIsReached() {
            //given
            LogCaptor logCaptor = LogCaptor.forClass(SMSService.class);

            //when
            when(smsLimitServiceMock.isBelowLimit(any(LocalDateTime.class))).thenReturn(false);

            //then
            smsService.sendSMS(alertDescription, validPhoneNumber);

            List<String> infoLogs = logCaptor.getInfoLogs();
            assertThat(infoLogs.size()).isEqualTo(1);
            assertThat(infoLogs.getFirst()).isEqualTo("Nie wysłano powiadomienia SMS");
        }

        @Test
        void shouldTriggeredLogger_whenSMSMessageIsToLong() {
            //given
            LogCaptor logCaptor = LogCaptor.forClass(SMSService.class);

            //when
            when(smsLimitServiceMock.isBelowLimit(any(LocalDateTime.class))).thenReturn(false);

            //then
            smsService.sendSMS(tooLongDescription,validPhoneNumber);
            List<String> logs = logCaptor.getLogs();

            logs.forEach(System.out::println);

            assertThat(logs.size()).isEqualTo(2);
            assertThat(logs.get(0)).isEqualTo("Opis zdarzenia za długi");
            assertThat(logs.get(1)).isEqualTo("Nie wysłano powiadomienia SMS");
        }

        @Test
        void shouldTriggeredLogger_whenPhoneNumberIsNotValid() {
            //given
            LogCaptor logCaptor = LogCaptor.forClass(SMSService.class);

            //when
            when(smsLimitServiceMock.isBelowLimit(any(LocalDateTime.class))).thenReturn(false);

            //then
            smsService.sendSMS(alertDescription,notValidPhoneNumber);
            List<String> logs = logCaptor.getLogs();

            logs.forEach(System.out::println);

            assertThat(logs.size()).isEqualTo(2);
            assertThat(logs.get(0)).isEqualTo("Zły format numeru telefonu. Numer powinien posiadać prefix '+48' oraz kolejno 9 cyfr");
            assertThat(logs.get(1)).isEqualTo("Nie wysłano powiadomienia SMS");
        }
    }

    @Nested
    class SMSValidatorTests {
        private final String tooLongDescription = """ 
                                      Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                                      Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                                      Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris 
                                      nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in 
                                      reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla 
                                      pariatur. Excepteur sint occaecat cupidatat non proident, sunt in 
                                      culpa qui officia deserunt mollit anim id est laborum.
                                        """;
        private final String notValidPhoneNumber = "+481234567890";

        @Test
        void shouldThrownException_whenPhoneNumberIsNotValid() {
                     //then
            Assertions.assertThatThrownBy(() -> SMSValidator.validate(alertDescription, notValidPhoneNumber)).isExactlyInstanceOf(SMSNotSentException.class);
        }

        @Test
        void shouldThrownException_whenDescriptionIsToLong() {
            //when
            when(smsLimitServiceMock.isBelowLimit(any(LocalDateTime.class))).thenReturn(false);

            //then
            Assertions.assertThatThrownBy(() -> SMSValidator.validate(tooLongDescription, notValidPhoneNumber)).isExactlyInstanceOf(SMSNotSentException.class);
        }
    }

    @Nested
    class SMSLimitServiceTests {

    }
}