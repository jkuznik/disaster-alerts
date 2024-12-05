package pl.ateam.disasteralerts.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class SMSService implements NotificationListener {

    private final SMSLimitService smsLimitService;
    private final TwilioClient twilioClient;

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, UserDTO interestedUser) {
            sendSMS(alertAddDTO.description(), interestedUser.phoneNumber());
    }

    public void sendSMS(String alertDescription, String phoneNumber) {
        boolean SMSLimitIsBelow = false;
        LocalDateTime today = LocalDateTime.now();

        try {
            SMSValidator.validate(alertDescription, phoneNumber);
            SMSLimitIsBelow = smsLimitService.isBelowLimit(today);
        } catch (SMSNotSentException e) {
            log.info(e.getMessage(), e.getCause());
        }

        if(SMSLimitIsBelow){
            twilioClient.sendSMS(alertDescription, phoneNumber);
            smsLimitService.increaseLimit(today);
        } else {
            log.info("Nie wysłano powiadomienia SMS");
        }
    }
}

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sms_limits")
class SMSLimit extends EntityAudit {

    @Column(nullable = false)
    private int limitCounter;

    public int increaseCounter(){
        return limitCounter++;
    }
}

@Repository
interface SMSLimitRepository extends JpaRepository<SMSLimit, UUID> {
    @Query(value = "SELECT * FROM sms_limits WHERE CAST(create_date AS date) = CAST(:date AS date)", nativeQuery = true)
    Optional<SMSLimit> findByExactDay(@Param("date") LocalDateTime date);
}

@RequiredArgsConstructor
@Service
class SMSLimitService {

    private final SMSLimitRepository smsLimitRepository;

    @Transactional
    boolean isBelowLimit(LocalDateTime date) {
        boolean result = true;

        Optional<SMSLimit> byExactDay = smsLimitRepository.findByExactDay(date);
        if(byExactDay.isPresent()){
            result = byExactDay.get().getLimitCounter() < Integer.parseInt(System.getenv("DAY_SMS_LIMIT"));
        } else {
            createLimiter();
        }

        return result;
    }

    @Transactional
    int increaseLimit(LocalDateTime date) {
        SMSLimit smsLimit = smsLimitRepository.findByExactDay(date).orElseThrow(
                () -> new RuntimeException("Something go wrong with increase " + date + " SMS limit")
        );

        return smsLimit.increaseCounter();
    }

    @Transactional
    private void createLimiter() {
       smsLimitRepository.save(SMSLimit.builder()
                .limitCounter(0)
                .build());
    }
}

@Component
class TwilioClient {

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String TWILIO_PHONE_NUMBER = System.getenv("TWILIO_PHONE_NUMBER");

    void sendSMS(String alertDescription, String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        alertDescription
                )
                .create();
    }
}

class SMSValidator {
    static void validate(String alertDescription, String phoneNumber) throws SMSNotSentException {
        if (alertDescription.length() > 255) {
            throw new SMSNotSentException("Opis zdarzenia za długi");
        }

        if (!phoneNumber.matches("\\+48\\d{9}")) {
            throw new SMSNotSentException("Zły format numeru telefonu. Numer powinien posiadać prefix '+48' oraz kolejno 9 cyfr");
        }
    }
}