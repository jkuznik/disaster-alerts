package pl.ateam.disasteralerts.disasteralert;

import java.time.Instant;
import java.util.UUID;

record AlertDTO(UUID id,
                DisasterType type,
                String location,
                Instant creationDate,
                DisasterStatus status) {
}
