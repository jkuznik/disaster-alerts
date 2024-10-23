package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(@Size(min=5, max=15)
                              @NotBlank
                              String username,
                              @Email @NotBlank
                              String email,
                              @NotNull(message = "Wybierz  miejscowość")
                              String location,
                              @NotBlank @Size(min = 6, max = 255)
                              String password) {
}
