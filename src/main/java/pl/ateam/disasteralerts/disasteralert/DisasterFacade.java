package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Component
@Validated
@RequiredArgsConstructor
public class DisasterFacade {
    private final DisasterService disasterService;
    private final AlertFacade alertFacade;

    public DisasterDTO addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO) {
        DisasterDTO disasterDTO = disasterService.addDisaster(disasterAddDTO);

        AlertAddDTO alertAddDTO = new AlertAddDTO(
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location(),
                disasterDTO.disasterStartTime());

        alertFacade.addAlert(alertAddDTO);

        return disasterDTO;
    }
}
