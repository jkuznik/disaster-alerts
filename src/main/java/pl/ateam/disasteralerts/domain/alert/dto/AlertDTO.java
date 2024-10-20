package pl.ateam.disasteralerts.domain.alert.dto;

import java.util.UUID;

public record AlertDTO(UUID disasterId,
                String description,
                String location,
                String username) {
}
