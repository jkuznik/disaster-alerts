package pl.ateam.disasteralerts.disaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.disaster.enums.DisasterType;

import java.time.LocalDateTime;

public record DisasterAddDTO(@NotNull DisasterType type,
                             @NotNull String description,
                             @NotNull @NotBlank String source,
                             @NotNull @NotBlank String location,
                             @NotNull LocalDateTime disasterStartTime,
                             @NotNull DisasterStatus status,
                             @NotNull @NotBlank String userEmail) {
}
