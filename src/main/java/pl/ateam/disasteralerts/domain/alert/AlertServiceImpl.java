package pl.ateam.disasteralerts.domain.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.domain.alertUser.AlertUserFacade;
import pl.ateam.disasteralerts.domain.disaster.DisasterFacade;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterDTO;
import pl.ateam.disasteralerts.domain.user.UserFacadeWydmuszka;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository repository;
    private final UserFacadeWydmuszka userFacadeWydmuszka;
    private final AlertMapper mapper;

    private final AlertManager alertManager;
    private final SMSService smsService;
    private final EmailService emailService;

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

        alertManager.addAlertListener(smsService);
        alertManager.createAlert(alertAddDTO, interestedUsers);
    }
}
