package pl.ateam.disasteralerts.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacadeWydmuszka {

    private final UserService userService;

    //TODO: propozycja aby uwtorzyc UserEmailDto, obiekt który zwroci sam emil usera
    List<UserDTO> getUsersByDisasterType(DisasterType disasterType) {
        //TODO: user powinien posiadać List<DisasterType> na potrzeby zdefiniowania które zagrożenia go interesują

//        userService.findAllByDisasterType(disasterType);
//
//        ta metoda będzie pomocna albo inaczej wyciągamy wszystkich użytkowników a potem filtrujemy po polu
//        DisasterType i zwracamy liste
//
        return List.of();
    }

    List<UserDTO> getUsersByLocation(String location) {

//        userService.findAllByLocation(location);
//        ta metoda analogicznie do powyższej
        return List.of();
    }
}
