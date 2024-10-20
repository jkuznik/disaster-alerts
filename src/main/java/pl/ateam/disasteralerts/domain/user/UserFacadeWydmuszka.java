package pl.ateam.disasteralerts.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
