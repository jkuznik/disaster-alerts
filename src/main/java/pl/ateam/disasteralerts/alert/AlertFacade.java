package pl.ateam.disasteralerts.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.alert.dto.AlertDTO;

@Component
@RequiredArgsConstructor
public class AlertFacade {

    private final AlertServiceImpl alertService;

    public AlertDTO createAlert(AlertAddDTO alertAddDTO) {
        return alertService.createAlert(alertAddDTO);
    }
}
