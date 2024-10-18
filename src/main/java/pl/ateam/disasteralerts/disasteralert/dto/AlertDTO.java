package pl.ateam.disasteralerts.disasteralert.dto;

import java.time.Instant;
import java.util.UUID;

public record AlertDTO(UUID id,
                UUID disasterId,
                String location,
                Instant creationDate) {
}
