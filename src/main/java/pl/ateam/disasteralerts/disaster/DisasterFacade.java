package pl.ateam.disasteralerts.disaster;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Validated
@RequiredArgsConstructor
public class DisasterFacade {
    private final DisasterService disasterService;

    public DisasterDTO createDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO, @NotNull @NotBlank String source) {
        return disasterService.createDisaster(disasterAddDTO, source);
    }

    public List<DisasterDTO> interestingDisasters(Optional<DisasterType> type, Optional<String> location) {
        return disasterService.interestingDisasters(type, location);
    }

    public Map<String, Integer> inLocationDisastersAmount() {
        return disasterService.inLocationDisastersAmount();
    }

    public Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(DisasterType type, String location) {
        return disasterService.getActiveDisasterForTypeAndLocation(type, location);
    }
}
