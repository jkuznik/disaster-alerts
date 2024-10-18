package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import pl.ateam.disasteralerts.user.Role;

public record UserDTO(
                        @Size(min=5, max=15)
                        @NotBlank
                        String username,
                        @Email @NotBlank
                        @Max(255)
                        String email,
                        @NotBlank @Size(min = 6, max = 255)
                        String password,
                        @Size(min=9,max=9)
                        String phoneNumber,
                        @Max(255)
                        String location,
                        String role) {
}
