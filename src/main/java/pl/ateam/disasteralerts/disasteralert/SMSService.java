package pl.ateam.disasteralerts.disasteralert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class SMSService implements AlertListener {

    SMSLimitService smsLimitService;

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String TWILIO_PHONE_NUMBER = System.getenv("TWILIO_PHONE_NUMBER");

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {
        interestedUsers.forEach(userDTO -> {
            sendSMS(alertAddDTO.description(), userDTO.phoneNumber());
        });
    }

    public void sendSMS(String alertDescription, String phoneNumber) {
        LocalDateTime today = LocalDateTime.now();

        if(smsLimitService.isLimitReached(today)){
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message
                    .creator(
                            new PhoneNumber(phoneNumber),
                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                            alertDescription
                    )
                    .create();

            System.out.println(message.getSid());

            smsLimitService.increaseLimit(today);
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
class SMSLimit {

    @Column(nullable = false)
    private int limitCounter;

    public void increaseCounter(){
        limitCounter++;
    }
}

@Repository
interface SMSLimitRepository extends JpaRepository<SMSLimit, UUID> {
    @Query("SELECT s FROM SMSLimit s WHERE CAST(s.createDate AS date) = CAST(:date AS date)")
    Optional<SMSLimit> findByExactDay(@Param("date") LocalDateTime date);
}

@RequiredArgsConstructor
@Service
class SMSLimitService {

    SMSLimitRepository smsLimitRepository;

    boolean isLimitReached(LocalDateTime date) {
        SMSLimit currentSmsLimit = smsLimitRepository.findByExactDay(date)
                .orElse(createLimiter());

        return currentSmsLimit.getLimitCounter() < 10;
    }

    @Transactional
    void increaseLimit(LocalDateTime date) {
        SMSLimit smsLimit = smsLimitRepository.findByExactDay(date).orElseThrow(
                () -> new RuntimeException("Something go wrong with increase " + date + " SMS limit")
        );

        smsLimit.increaseCounter();
    }

    private SMSLimit createLimiter() {
        return smsLimitRepository.save(SMSLimit.builder()
                .limitCounter(1)
                .build());
    }
}