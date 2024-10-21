package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

public record DisasterAddWebDTO(@NotNull DisasterType type,
                                @NotNull String description,
                                @NotNull @NotBlank String location,
                                @NotNull @NotBlank String userEmail) {
}