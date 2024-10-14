package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

import java.time.Instant;
import java.util.UUID;

public record DisasterAddDTO(@NotNull UUID id,
                             @NotNull DisasterType type,
                             @NotNull @NotBlank String source,
                             @NotNull @NotBlank String location,
                             @NotNull Instant disasterStartTime,
                             @NotNull DisasterStatus status) {
}
