package pl.ateam.disasteralerts.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class SMSService implements AlertListener {

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {
        interestedUsers.forEach(interestedUser -> {
            sendSMS(interestedUser.phoneNumber(), alertAddDTO.description());
        });
    }

    private void sendSMS(String phoneNumber, String message) {
        System.out.println(phoneNumber + "       " + message);
    }
}
