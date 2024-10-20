package pl.ateam.disasteralerts.user.dto;

public record UserDTO(String username,
                      String email,
                      String password,
                      String phoneNumber,
                      String location,
                      String role) {
}
