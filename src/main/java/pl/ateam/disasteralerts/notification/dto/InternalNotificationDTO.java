package pl.ateam.disasteralerts.notification.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InternalNotificationDTO(@NotNull UUID userId,
                                      @NotNull UUID alertId,
                                      @NotNull String alertDescription) {
}
