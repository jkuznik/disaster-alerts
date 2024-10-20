package pl.ateam.disasteralerts.disaster.dto;

import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.disaster.enums.DisasterType;

import java.time.LocalDateTime;
import java.util.UUID;

public record DisasterDTO(UUID id,
                   DisasterType type,
                   String description,
                   String source,
                   String location,
                   LocalDateTime disasterStartTime,
                   LocalDateTime disasterEndTime,
                   DisasterStatus status,
                   String userEmail) {
}
