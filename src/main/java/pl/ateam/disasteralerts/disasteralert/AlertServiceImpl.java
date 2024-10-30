package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;
import pl.ateam.disasteralerts.user.UserFacade;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final NotificationManager notificationManager;
    private final UserFacade userFacade;
    private final AlertMapper mapper;

    @Transactional
    @Override
    public AlertDTO createAlert(AlertAddDTO alertAddDTO) {
        Alert alert = mapper.mapAlertAddDtoToAlert(alertAddDTO);
        AlertDTO alertDTO = mapper.mapAlertToAlertDto(alertRepository.save(alert));
        alertRepository.save(alert);

        sendNotifications(alertAddDTO);

        return alertDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void sendNotifications(AlertAddDTO alertAddDTO) {
        Set<UserDTO> interestedUsers = userFacade.getInterestedUsers(alertAddDTO.location());

        notificationManager.addEmailService();
//        notificationManager.addSMSService();  //TODO: temporary mute after test functionality
        notificationManager.createAlert(alertAddDTO, interestedUsers);
    }
}
