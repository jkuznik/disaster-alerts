package pl.ateam.disasteralerts.domain.alert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;

@Validated
interface AlertService {
    AlertDTO addAlert(@NotNull @Valid AlertAddDTO alertAddDTO);
}
