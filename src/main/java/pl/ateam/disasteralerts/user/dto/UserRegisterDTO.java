package pl.ateam.disasteralerts.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(@Size(min=5, max=15)
                              @NotBlank
                              String username,
                              @Email @NotBlank
                              String email,
                              @NotNull(message = "Wybierz  miejscowość")
                              String location,
                              @Pattern(
                                      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{6,25}$",
                                      message = "Hasło musi zawierać co najmniej jedną cyfrę, małą literę, wielką literę, znak specjalny oraz mieścić się w przedziale 6-25 znaków"
                              )
                              String password) {
}
