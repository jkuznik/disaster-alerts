package pl.ateam.disasteralerts.user.dto;

import pl.ateam.disasteralerts.user.Role;

public record UserDTO(String username,
                      String email,
                      String password,
                      String phoneNumber,
                      String location,
                      String role) {
}
