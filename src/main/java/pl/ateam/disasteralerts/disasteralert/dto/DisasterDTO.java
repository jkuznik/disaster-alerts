package pl.ateam.disasteralerts.disasteralert.dto;

import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

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
