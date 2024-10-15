package pl.ateam.disasteralerts.disasteralert.roboczeKlasyWydmuszki;


import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.user.UserDTO;

@Component
public class UserFacadeWydmuszka {

    public UserDTO getUserDtoByUsername(String username) {
        return new UserDTO(
                "test",
                "test",
                "test",
                "test");
    }
}
