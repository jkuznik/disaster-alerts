package pl.ateam.disasteralerts.domain.disaster.dto;

import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record DisasterDTO(UUID id,
                   DisasterType type,
                   String description,
                   String source,
                   String location,
                   Instant disasterStartTime,
                   Instant disasterEndTime,
                   DisasterStatus status,
                   String userEmail) {
}