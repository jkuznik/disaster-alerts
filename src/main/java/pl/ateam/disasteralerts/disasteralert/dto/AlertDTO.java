package pl.ateam.disasteralerts.disasteralert.dto;

import java.util.UUID;

public record AlertDTO(UUID disasterId,
                       String description,
                       String location,
                       String username) {
}
