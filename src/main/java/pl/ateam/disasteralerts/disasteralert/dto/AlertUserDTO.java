package pl.ateam.disasteralerts.disasteralert.dto;

import java.util.UUID;

public record AlertUserDTO(UUID alertId,
                           String username) {
}
