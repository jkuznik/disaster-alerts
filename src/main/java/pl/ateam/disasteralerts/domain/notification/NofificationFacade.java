package pl.ateam.disasteralerts.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class NofificationFacade {

    private final AlertManager alertManager;
    private final EmailService emailService;
    private final SMSService smsService;


    public void sendNotification(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {
        alertManager.createAlert(alertAddDTO, interestedUsers);
    }

    public void addEmailNotification() {
        alertManager.addAlertListener(emailService);
    }

    public void addSMSNotification() {
        alertManager.addAlertListener(smsService);
    }

    public void removeEmailNotification() {
        alertManager.removeAlertListener(emailService);
    }

    public void removeSMSNotification() {
        alertManager.removeAlertListener(smsService);
    }
}
