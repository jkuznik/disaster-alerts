package pl.ateam.disasteralerts.domain.disaster;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterDTO;

@Validated
public interface DisasterService {
    DisasterDTO addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO);
}
