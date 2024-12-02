package pl.ateam.disasteralerts.alert.dto;

import java.util.UUID;

public record AlertDTO(UUID disasterId,
                       String description,
                       String location) {
}
