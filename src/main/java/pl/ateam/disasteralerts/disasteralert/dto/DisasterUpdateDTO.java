package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.ateam.disasteralerts.disasteralert.DisasterStatus;
import pl.ateam.disasteralerts.disasteralert.DisasterType;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public record DisasterUpdateDTO(@NotNull UUID id,
                                Optional<DisasterType> disasterType,
                                Optional<String> source,
                                Optional<Instant> disasterEndTime,
                                Optional<DisasterStatus> disasterStatus) {
}
