package pl.ateam.disasteralerts.disasteralert.dto;

import pl.ateam.disasteralerts.disasteralert.enums.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.enums.DisasterType;

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
