package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Set;

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