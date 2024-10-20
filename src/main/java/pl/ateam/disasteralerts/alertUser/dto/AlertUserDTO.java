package pl.ateam.disasteralerts.alertUser.dto;

import java.util.UUID;

public record AlertUserDTO(UUID alertId,
                           String username) {
}
