package pl.ateam.disasteralerts.disasteralert.dto;

import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;
import pl.ateam.disasteralerts.util.levelofrisk.RiskLevel;

import java.time.LocalDateTime;
import java.util.UUID;

public record DisasterDTO(UUID id,
                          DisasterType type,
                          RiskLevel riskLevel,
                          String description,
                          String source,
                          String location,
                          LocalDateTime createDate,
                          LocalDateTime disasterEndTime,
                          DisasterStatus status,
                          UUID userId) {
}
