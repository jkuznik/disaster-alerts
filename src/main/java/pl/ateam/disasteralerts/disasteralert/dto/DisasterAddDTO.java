package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

import java.time.LocalDateTime;
import java.util.UUID;

public record DisasterAddDTO(@NotNull DisasterType type,
                             @NotNull String description,
                             @NotNull @NotBlank String source,
                             @NotNull @NotBlank String location,
                             @NotNull LocalDateTime disasterStartTime,
                             @NotNull DisasterStatus status,
                             @NotNull UUID userId) {
}
