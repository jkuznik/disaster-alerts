package pl.ateam.disasteralerts.disasteralert;

import java.time.Instant;
import java.util.UUID;

record AlertDTO(UUID id,
                Disaster disaster,
                String location,
                Instant creationDate) {
}
