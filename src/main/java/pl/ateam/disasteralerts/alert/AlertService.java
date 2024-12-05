package pl.ateam.disasteralerts.alert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.alert.dto.AlertDTO;

@Validated
interface AlertService {
    AlertDTO createAlert(@NotNull @Valid AlertAddDTO alertAddDTO);
}
