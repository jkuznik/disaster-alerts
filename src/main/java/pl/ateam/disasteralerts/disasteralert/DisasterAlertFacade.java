package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Component
@Validated
@RequiredArgsConstructor
public class DisasterAlertFacade {
    private final DisasterService disasterService;

    public DisasterDTO addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO) {
        return disasterService.addDisaster(disasterAddDTO);
    }
}
