package pl.ateam.disasteralerts.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserFacadeWydmuszka {

    private final UserService userService;

    public Set<UserDTO> getInterestedUsers(DisasterType disasterType, String location) {
        Set<UserDTO> usersByDisasterType = getUsersByDisasterType(disasterType);
        Set<UserDTO> usersByLocation = getUsersByLocation(location);

        Set<UserDTO> interestedUsers = new HashSet<>();

        interestedUsers.addAll(usersByDisasterType);
        interestedUsers.addAll(usersByLocation);
        return interestedUsers;
    }


    //TODO: propozycja aby uwtorzyc UserEmailDto, obiekt który zwroci sam emil usera
    private Set<UserDTO> getUsersByDisasterType(DisasterType disasterType) {
        //TODO: user powinien posiadać List<DisasterType> na potrzeby zdefiniowania które zagrożenia go interesują

//        userService.findAllByDisasterType(disasterType);
//
//        ta metoda będzie pomocna albo inaczej wyciągamy wszystkich użytkowników a potem filtrujemy po polu
//        DisasterType i zwracamy liste
//
        return Set.of();
    }

    private Set<UserDTO> getUsersByLocation(String location) {

//        userService.findAllByLocation(location);
//        ta metoda analogicznie do powyższej
        return Set.of();
    }
}