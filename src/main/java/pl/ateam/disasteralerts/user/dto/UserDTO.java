package pl.ateam.disasteralerts.user.dto;

import pl.ateam.disasteralerts.user.Role;

import java.util.UUID;

public record UserDTO(UUID id,
                      String username,
                      String email,
                      String password,
                      String phoneNumber,
                      String location,
                      String role) {
}
