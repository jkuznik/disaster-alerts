package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.util.UUID;

@Validated
public interface DisasterService {
    DisasterDTO addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO);

    DisasterDTO getDisasterById(@NotNull UUID id);
}
