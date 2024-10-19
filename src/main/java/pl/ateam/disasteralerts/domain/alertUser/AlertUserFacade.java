package pl.ateam.disasteralerts.domain.alertUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserDTO;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlertUserFacade {

    private final AlertUserService alertUserService;

    public AlertUserDTO addAlertUser(AlertUserAddDTO alertUserAddDTO) {
        return alertUserService.addAlertUser(alertUserAddDTO);
    }

    public List<AlertUserDTO> getAlertUsersByAlertId(UUID alertId) {
        return alertUserService.getAllByAlertId(alertId);
    }

    public List<AlertUserDTO> getAlertUserByUserId(UUID userId) {
        return alertUserService.getAllByUserId(userId);
    }
}
