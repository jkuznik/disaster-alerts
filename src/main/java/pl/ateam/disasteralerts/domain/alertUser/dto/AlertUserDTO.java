package pl.ateam.disasteralerts.domain.alertUser.dto;

import java.util.UUID;

public record AlertUserDTO(UUID alertId,
                           UUID userId) {
}
