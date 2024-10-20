package pl.ateam.disasteralerts.domain.alertUser.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlertUserAddDTO(@NotNull UUID id,
                              @NotNull UUID alertId,
                              @NotNull String username) {
}
