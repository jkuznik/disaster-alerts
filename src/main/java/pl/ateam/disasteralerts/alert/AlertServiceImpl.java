package pl.ateam.disasteralerts.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.notification.NotificationFacade;
import pl.ateam.disasteralerts.user.UserFacade;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final NotificationFacade notificationFacade;
    private final UserFacade userFacade;
    private final AlertMapper mapper;

    @Transactional
    @Override
    public AlertDTO createAlert(AlertAddDTO alertAddDTO) {
        Alert alert = alertRepository.save(
                mapper.mapAlertAddDtoToAlert(alertAddDTO));

        //TODO: powiadomienia są wysyłane przed zakończeniem tranzakcji, to umożliwia wysłanie powiadomień
        // nawet jeżeli alert nie zostanie zapisany w bazie
        sendNotifications(alertAddDTO);

        return mapper.mapAlertToAlertDto(alert);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void sendNotifications(AlertAddDTO alertAddDTO) {
        Set<UserDTO> interestedUsers = userFacade.getInterestedUsers(alertAddDTO.location());

        interestedUsers.forEach(user -> {
            if (user.phoneNumber() != null && !user.phoneNumber().isEmpty()) {
                notificationFacade.addSMSService();
            }
            if (user.email() != null && !user.email().isEmpty()) {
                notificationFacade.addEmailService();
            }

            //TODO: dodać możliwość wyboru sposobu otrzymania powiadomień przez użytkownika
            notificationFacade.addInternalNotificationService();
            notificationFacade.sendNotifications(alertAddDTO, user);
            notificationFacade.clearNotificationServices();
        });
    }
}
