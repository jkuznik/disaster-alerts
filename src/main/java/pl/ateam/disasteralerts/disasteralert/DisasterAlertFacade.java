package pl.ateam.disasteralerts.disasteralert;

import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Component
public class DisasterAlertFacade {

    private final DisasterServiceAPI disasterServiceAPI;

    public DisasterAlertFacade(DisasterServiceAPI disasterServiceAPI) {
        this.disasterServiceAPI = disasterServiceAPI;
    }

    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO) {
        return disasterServiceAPI.addDisaster(disasterAddDTO);
    }
}
