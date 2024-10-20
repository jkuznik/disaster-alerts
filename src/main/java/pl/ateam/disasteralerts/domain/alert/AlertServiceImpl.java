package pl.ateam.disasteralerts.domain.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.domain.notification.NofificationFacade;
import pl.ateam.disasteralerts.domain.user.UserFacadeWydmuszka;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository repository;
    private final UserFacadeWydmuszka userFacadeWydmuszka;
    private final NofificationFacade nofificationFacade;
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

        nofificationFacade.addEmailNotification();
        nofificationFacade.addSMSNotification();
        nofificationFacade.sendNotification(alertAddDTO, interestedUsers);
    }
}
