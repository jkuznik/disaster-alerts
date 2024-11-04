package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Size(min = 5, max = 255)
        @NotBlank
        String firstName,
        @Size(min = 5, max = 255)
        @NotBlank
        String lastName,
        @Email
        @NotBlank
        @Size(max = 255)
        String email,

        @Pattern(
                regexp = "^(\\+48)?\\d{9}$",
                message = "Nieprawidłowy format numeru telefonu")
        String phoneNumber,

        @Size(max = 255)
        @NotBlank
        String location
) {
}
