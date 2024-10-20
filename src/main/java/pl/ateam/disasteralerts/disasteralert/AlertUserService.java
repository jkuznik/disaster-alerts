package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertUserDTO;

import java.util.List;
import java.util.UUID;

@Validated
public interface AlertUserService {

    AlertUserDTO addAlertUser(@NotNull @Valid AlertUserAddDTO alertUser);
    List<AlertUserDTO> getAllByAlertId(@NotNull @Valid UUID alertId);
    List<AlertUserDTO> getAllByUsername(@NotNull String username);
}
