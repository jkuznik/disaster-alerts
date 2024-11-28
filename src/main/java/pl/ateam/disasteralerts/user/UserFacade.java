package pl.ateam.disasteralerts.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.user.dto.UserUpdateDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public Set<UserDTO> getInterestedUsers(String location) {
        //TODO: w tym miejscu można dodać inne kryteria wyszukiwania użytkowników, wstępnie jedynym kryterium jest
        // lokalizacja dlatego ta metoda 'wywołuje tylko jedno filtrowanie'

        return new HashSet<>(getAllUsersByLocation(location));
    }

    public Set<UserDTO> getAllUsersByLocation(String location) {
        return userService.findAllByLocation(location);
    }

    public UserUpdateDTO getUserForUpdate(UUID userId) {
        return userService.findUserUpdateDto(userId);
    }

    public void updateUser(UserUpdateDTO userUpdateDto, UUID userId) {
        userService.updateUserEntity(userUpdateDto, userId);
    }

    public void removePhoneNumber(String email) {
        userService.removePhoneNumber(email);
    }
}
