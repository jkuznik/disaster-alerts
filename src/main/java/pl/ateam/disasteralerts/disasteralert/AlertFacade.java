package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;

@Component
@Validated
@RequiredArgsConstructor
public class AlertFacade {

    private final AlertService alertService;

    AlertDTO addAlert(@Valid AlertAddDTO alertAddDTO){
        return alertService.addAlert(alertAddDTO);
    }
}
