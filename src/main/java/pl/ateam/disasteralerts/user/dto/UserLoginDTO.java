package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.*;

public record UserLoginDTO(
        @Email @NotBlank
        @Max(255)
        String email,
        String location,
        @NotBlank
        @Size(min=6,max=25)
        @Pattern(regexp = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 and 16 characters long")
        String password) {
}
