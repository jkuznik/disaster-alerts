package pl.ateam.disasteralerts.disasteralert.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlertAddDTO(@NotNull UUID disasterId,
                          @NotNull String description,
                          @NotNull String location) {
}
