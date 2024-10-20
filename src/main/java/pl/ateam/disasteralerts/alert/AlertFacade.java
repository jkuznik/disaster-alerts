package pl.ateam.disasteralerts.alert;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;

@Component
@Validated
@RequiredArgsConstructor
public class AlertFacade {

    private final AlertService alertService;

    public AlertDTO addAlert(@Valid AlertAddDTO alertAddDTO){
        return alertService.addAlert(alertAddDTO);
    }
}
