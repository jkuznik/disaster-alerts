package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

public record DisasterUpdateDTO(@NotNull DisasterType disasterType,
                                @NotNull @NotBlank String source,
                                @NotNull DisasterStatus disasterStatus) {
}
