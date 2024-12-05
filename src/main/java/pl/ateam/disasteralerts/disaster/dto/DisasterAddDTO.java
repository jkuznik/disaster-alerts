package pl.ateam.disasteralerts.disaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.util.enums.DisasterType;

import java.util.UUID;

public record DisasterAddDTO(@NotNull DisasterType type,
                             @NotNull String description,
                             @NotNull @NotBlank String location,
                             @NotNull UUID userId) {
}
