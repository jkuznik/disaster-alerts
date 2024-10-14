package pl.ateam.disasteralerts.user;

public record UserDTO(String username,
                      String email,
                      String phoneNumber,
                      String location) {
}
