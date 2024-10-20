package pl.ateam.disasteralerts._config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.user.UserService;
import pl.ateam.disasteralerts.user.dto.UserDTO;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDTO userDTO = userService.findByEmail(email);
        return AppUser.builder()
                .userDTO(userDTO)
                .build();
    }
}