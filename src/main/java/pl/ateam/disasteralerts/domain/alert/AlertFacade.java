package pl.ateam.disasteralerts.domain.alert;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;

@Component
@Validated
@RequiredArgsConstructor
public class AlertFacade {

    private final AlertService alertService;

    public AlertDTO addAlert(@Valid AlertAddDTO alertAddDTO){
        return alertService.addAlert(alertAddDTO);
    }
}
