package pl.ateam.disasteralerts.user.dto;

public record UserDTO(String username,
                      String email,
                      String phoneNumber,
                      String location) {
}
