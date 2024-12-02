package pl.ateam.disasteralerts.alert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlertAddDTO(@NotNull UUID disasterId,
                          @NotNull String description,
                          @NotNull @NotBlank String location) {
}

