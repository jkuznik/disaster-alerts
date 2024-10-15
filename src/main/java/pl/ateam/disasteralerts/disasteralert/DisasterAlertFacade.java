package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Component
@Validated
public class DisasterAlertFacade {

    private final DisasterService disasterService;

    public DisasterAlertFacade(DisasterService disasterService) {
        this.disasterService = disasterService;
    }

    public DisasterDTO addDisaster(@NotNull DisasterAddDTO disasterAddDTO) {
        return disasterService.addDisaster(disasterAddDTO);
    }
}
