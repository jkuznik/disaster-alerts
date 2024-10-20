package pl.ateam.disasteralerts.alertUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.alertUser.dto.AlertUserDTO;

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

    public List<AlertUserDTO> getAlertUserByUsername(String username) {
        return alertUserService.getAllByUsername(username);
    }
}
