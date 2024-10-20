package pl.ateam.disasteralerts.domain.alert.dto;

import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record AlertAddDTO(@NotNull UUID id,
                          @NotNull UUID disasterId,
                          @NotNull String description,
                          @NotNull String location,
                          @NotNull LocalDateTime creationDate) {
}
