package pl.ateam.disasteralerts.domain.alertUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserDTO;

import java.util.List;
import java.util.UUID;

@Validated
public interface AlertUserService {

    AlertUserDTO addAlertUser(@NotNull @Valid AlertUserAddDTO alertUser);
    List<AlertUserDTO> getAllByAlertId(@NotNull @Valid UUID alertId);
    List<AlertUserDTO> getAllByUsername(@NotNull String username);
}
