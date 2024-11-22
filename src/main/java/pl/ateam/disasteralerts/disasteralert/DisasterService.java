package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.util.List;
import java.util.Optional;

@Validated
interface DisasterService {
    DisasterDTO createDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO, @NotNull @NotBlank String source);
    Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(@NotNull @Valid DisasterType type, @NotNull @NotBlank String location);

    List<DisasterDTO> interestingDisasters(Optional<DisasterType> type, Optional<String> location);

}
