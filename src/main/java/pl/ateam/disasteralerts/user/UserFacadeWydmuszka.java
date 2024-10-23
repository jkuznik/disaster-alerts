package pl.ateam.disasteralerts.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFacadeWydmuszka {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Set<UserDTO> getInterestedUsers(String location) {
        Set<UserDTO> usersByLocation = getUsersByLocation(location);

        Set<UserDTO> interestedUsers = new HashSet<>();

        interestedUsers.addAll(usersByLocation);
        return interestedUsers;
    }

    private Set<UserDTO> getUsersByLocation(String location) {
        // TODO: robocza wersja aby cokolwiek zwróciło
        return userRepository.findAll().stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toSet());
    }
}
