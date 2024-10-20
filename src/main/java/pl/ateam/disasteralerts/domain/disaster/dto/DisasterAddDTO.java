package pl.ateam.disasteralerts.domain.disaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public record DisasterAddDTO(@NotNull DisasterType type,
                             @NotNull String description,
                             @NotNull @NotBlank String source,
                             @NotNull @NotBlank String location,
                             @NotNull LocalDateTime disasterStartTime,
                             @NotNull DisasterStatus status,
                             @NotNull @NotBlank String userEmail) {
}
