package pl.ateam.disasteralerts.disasteralert;

import java.time.Instant;
import java.util.UUID;

record DisasterDTO(UUID id,
                   DisasterType type,
                   String source,
                   String location,
                   Instant startTimeDisaster,
                   Instant endTimeDisaster,
                   DisasterStatus status) {
}
