package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.*;

import pl.ateam.disasteralerts.user.Role;

import java.util.UUID;

public record UserDTO(
                        @NotNull
                        UUID id,
                        @Size(min=5, max=15)
                        @NotBlank
                        String username,
                        @Email @NotBlank
                        @Max(255)
                        String email,
                        @NotBlank @Size(min = 6, max = 25)
                        @Pattern(
                                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{6,25}$",
                                message = "Hasło musi zawierać co najmniej jedną cyfrę, małą literę, wielką literę, znak specjalny oraz mieścić się w przedziale 6-25 znaków"
                        )
                        String password,
                        @Pattern(
                                regexp = "^(\\+48)?\\d{9}$",
                                message = "Nieprawidłowy format numeru telefonu"
                        )
                        String phoneNumber,
                        @Max(255)
                        @NotBlank
                        String location,
                        String role) {
}
