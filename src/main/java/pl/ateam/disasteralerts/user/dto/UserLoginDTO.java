package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @Email @NotBlank
        @Max(255)
        String email,
        @NotBlank
        @Size(min=6,max=255)
        String password) {
}
