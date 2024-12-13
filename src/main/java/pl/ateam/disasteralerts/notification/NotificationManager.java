package pl.ateam.disasteralerts.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class NotificationManager {

    private final EmailService emailService;
    private final SMSService smsService;
    private final InternalNotificationService internalNotificationService;

    private final List<NotificationListener> notificationListeners = new ArrayList<>();
    void sendNotifications(AlertAddDTO alertAddDTO, UserDTO interestedUser) {
        notificationListeners.forEach(notificationListener -> notificationListener.addedAlert(alertAddDTO, interestedUser));
    }

    void addEmailService() {
        notificationListeners.add(emailService);
    }

    void addSMSService() {
        notificationListeners.add(smsService);
    }

    void addInternalNotificationsService() { notificationListeners.add(internalNotificationService);}

    void removeEmailService() {
        notificationListeners.remove(emailService);
    }

    void removeSMSService() {
        notificationListeners.remove(smsService);
    }

    void removeInternalNotificationsService() { notificationListeners.remove(internalNotificationService);}

    void clearNotificationServices() {
        notificationListeners.clear();
    }
}