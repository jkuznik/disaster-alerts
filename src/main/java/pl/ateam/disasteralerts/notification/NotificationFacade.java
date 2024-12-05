package pl.ateam.disasteralerts.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

@Component
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationManager notificationManager;

    public void sendNotifications(AlertAddDTO alertAddDTO, UserDTO interestedUser) {
        notificationManager.sendNotifications(alertAddDTO, interestedUser);
    }

    public void addEmailService() {
        notificationManager.addEmailService();
    }

    public void addSMSService() {
        notificationManager.addSMSService();
    }

    public void removeEmailService() {
        notificationManager.removeEmailService();
    }

    public void removeSMSService() {
        notificationManager.removeSMSService();
    }

    public void clearNotificationServices() {
        notificationManager.clearNotificationServices();
    }
}
