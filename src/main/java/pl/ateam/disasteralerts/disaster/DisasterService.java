package pl.ateam.disasteralerts.disaster;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;
import pl.ateam.disasteralerts.util.enums.DisasterType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
interface DisasterService {
    DisasterDTO createDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO, @NotNull @NotBlank String source);
    Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(@NotNull @Valid DisasterType type, @NotNull @NotBlank String location);

    List<DisasterDTO> interestingDisasters(Optional<DisasterType> type, Optional<String> location);

    Map<String, Integer> inLocationDisastersAmount();

}
