package pl.ateam.disasteralerts.domain.alertUser;

import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserDTO;

import java.util.List;
import java.util.UUID;

@Validated
public interface AlertUserService {

    AlertUserDTO addAlertUser(AlertUserAddDTO alertUser);
    List<AlertUserDTO> getAllByAlertId(UUID id);
    List<AlertUserDTO> getAllByUserId(UUID userId);
}
