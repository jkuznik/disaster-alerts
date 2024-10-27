package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;
import pl.ateam.disasteralerts.user.UserFacadeWydmuszka;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository repository;
    private final NotificationManager notificationManager;
    private final UserFacadeWydmuszka userFacadeWydmuszka;
    private final AlertMapper mapper;

    @Transactional
    @Override
    public AlertDTO addAlert(AlertAddDTO alertAddDTO) {
        Alert alert = mapper.mapAlertAddDtoToAlert(alertAddDTO);
        AlertDTO alertDTO = mapper.mapAlertToAlertDto(repository.save(alert));

        sendNotifications(alertAddDTO);
        return alertDTO;
    }

    private void sendNotifications(AlertAddDTO alertAddDTO) {
        String hardCodeLocationForTest = "Warszawa";
        Set<UserDTO> interestedUsers = userFacadeWydmuszka.getInterestedUsers( /*alertAddDTO.location()*/ hardCodeLocationForTest);

        notificationManager.addEmailService();
        notificationManager.addSMSService();
        notificationManager.createAlert(alertAddDTO, interestedUsers);
    }
}
