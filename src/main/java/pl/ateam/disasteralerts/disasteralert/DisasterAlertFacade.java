package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Validated
@RequiredArgsConstructor
public class DisasterAlertFacade {
    private final DisasterService disasterService;
    private final AlertService alertService;

    public void addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO) {
        disasterService.addDisaster(disasterAddDTO);
    }
}
