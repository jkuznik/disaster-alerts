package pl.ateam.disasteralerts.disaster.dto;

import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.disaster.DisasterType;

import java.time.LocalDateTime;
import java.util.UUID;

public record DisasterDTO(UUID id,
                          DisasterType type,
                          String description,
                          String source,
                          String location,
                          LocalDateTime createDate,
                          LocalDateTime disasterEndTime,
                          DisasterStatus status,
                          UUID userId) {
}
