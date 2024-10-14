package pl.ateam.disasteralerts.disasteralert;

import java.time.Instant;
import java.util.UUID;

public record AlertDTO(UUID id,
                Disaster disaster,
                String location,
                Instant creationDate) {
}
