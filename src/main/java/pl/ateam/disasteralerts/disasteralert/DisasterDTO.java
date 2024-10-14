package pl.ateam.disasteralerts.disasteralert;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record DisasterDTO(UUID id,
                   DisasterType type,
                   String source,
                   String location,
                   Instant disasterStartTime,
                   Instant disasterEndTime,
                   DisasterStatus status,
                   List<AlertDTO> alerts) {
}
