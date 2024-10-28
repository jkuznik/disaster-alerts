package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.util.Optional;

@Validated
interface DisasterService {
    DisasterDTO createDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO);
    Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(@NotNull @Valid DisasterType type, String location);
}
