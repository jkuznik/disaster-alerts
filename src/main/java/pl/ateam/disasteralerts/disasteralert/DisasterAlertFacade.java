package pl.ateam.disasteralerts.disasteralert;

import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Component
public class DisasterAlertFacade {

    private final DisasterService disasterService;

    public DisasterAlertFacade(DisasterService disasterService) {
        this.disasterService = disasterService;
    }

    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO) {
        return disasterService.addDisaster(disasterAddDTO);
    }
}
