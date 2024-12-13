package pl.ateam.disasteralerts.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.notification.dto.InternalNotificationDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationManager notificationManager;
    private final InternalNotificationService internalNotificationService;

    public void sendNotifications(AlertAddDTO alertAddDTO, UserDTO interestedUser) {
        notificationManager.sendNotifications(alertAddDTO, interestedUser);
    }

    public List<InternalNotificationDTO> getAllInternalNotifications (UUID userId){
        return internalNotificationService.getAllIN(userId);
    }

    public void addEmailService() {
        notificationManager.addEmailService();
    }

    public void addSMSService() {
        notificationManager.addSMSService();
    }

    public void addInternalNotificationService() {notificationManager.addInternalNotificationsService();}

    public void removeEmailService() {
        notificationManager.removeEmailService();
    }

    public void removeSMSService() {
        notificationManager.removeSMSService();
    }

    public void removeInternalNotificationService() {notificationManager.removeInternalNotificationsService();}

    public void clearNotificationServices() {
        notificationManager.clearNotificationServices();
    }
}
