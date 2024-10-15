package pl.ateam.disasteralerts.disasteralert.dto;

import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

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
