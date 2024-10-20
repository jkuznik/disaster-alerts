package pl.ateam.disasteralerts.disaster;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.alert.AlertFacade;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Validated
@RequiredArgsConstructor
public class DisasterFacade {
    private final DisasterService disasterService;
    private final AlertFacade alertFacade;

    public DisasterDTO addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO) {
        DisasterDTO disasterDTO = disasterService.addDisaster(disasterAddDTO);

        AlertAddDTO alertAddDTO = new AlertAddDTO(
                UUID.randomUUID(),
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location(),
                LocalDateTime.now());

        alertFacade.addAlert(alertAddDTO);

        return disasterDTO;
    }

    public DisasterDTO getDisasterById(@NotNull UUID id) {
        return disasterService.getDisasterById(id);
    }
}
