package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.*;

import pl.ateam.disasteralerts.user.Role;

public record UserDTO(
                        @Size(min=5, max=15)
                        @NotBlank
                        String username,
                        @Email @NotBlank
                        @Max(255)
                        String email,
                        @NotBlank @Size(min = 6, max = 255)
                        @Pattern(
                                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{6,25}$",
                                message = "Hasło musi zawierać co najmniej jedną cyfrę, małą literę, wielką literę, znak specjalny oraz mieścić się w przedziale 6-25 znaków"
                        )
                        String password,
                        @Size(min=9,max=9)
                        String phoneNumber,
                        @Max(255)
                        @NotBlank
                        String location,
                        String role) {
}
