package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlertUpdateDto(@NotNull UUID id,
                             @NotNull String description) {
}
