package pl.ateam.disasteralerts.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public Set<UserDTO> getInterestedUsers(String location) {
        //TODO: w tym miejscu można dodać inne kryteria wyszukiwania użytkowników, wstępnie jedynym kryterium jest
        // lokalizacja dlatego ta metoda 'wywołuje tylko jedno filtrowanie'

        return new HashSet<>(getAllUsersByLocation(location));
    }

    public List<UserDTO> getAllUsersByLocation(String location) {
        return userService.findAllByLocation(location);
    }
}
