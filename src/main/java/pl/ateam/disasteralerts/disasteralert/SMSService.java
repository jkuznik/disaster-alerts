package pl.ateam.disasteralerts.disasteralert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
class SMSService implements AlertListener {

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String MY_PHONE_NUMBER = System.getenv("MY_PHONE_NUMBER");
    public static final String TWILIO_PHONE_NUMBER = System.getenv("TWILIO_PHONE_NUMBER");

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {
        interestedUsers.forEach(userDTO -> {
            sendSMS(alertAddDTO.description(), userDTO.phoneNumber());
        });

        // TODO: docelowo powinna zadziałać powyższa część kodu, poniżej do testu podaję swój nr
        // sendSMS(alertAddDTO.description(), MY_PHONE_NUMBER);
    }

    public static void sendSMS(String alertDescription, String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        alertDescription
                )
                .create();

        System.out.println(message.getSid());
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
    private int counter;
}

@Repository
interface SMSLimitRepository extends JpaRepository<SMSLimit, UUID> {
    Optional<SMSLimit> findByCreateDate(LocalDateTime date);
}

@RequiredArgsConstructor
@Service
class SMSLimitService {

    SMSLimitRepository smsLimitRepository;

}