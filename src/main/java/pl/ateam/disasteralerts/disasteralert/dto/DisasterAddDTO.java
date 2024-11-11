package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disasteralert.DisasterType;
import pl.ateam.disasteralerts.util.levelofrisk.RiskLevel;

import java.util.UUID;

public record DisasterAddDTO(@NotNull DisasterType type,
                             @NotNull RiskLevel riskLevel,
                             @NotNull String description,
                             @NotNull @NotBlank String location,
                             @NotNull UUID userId) {
}
