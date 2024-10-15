package pl.ateam.disasteralerts.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.user.UserService;
import pl.ateam.disasteralerts.user.dto.UserLoginDTO;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserLoginDTO userDTO = userService.findByEmail(email);
        return User.builder()
                .username(userDTO.email())
                .password(passwordEncoder.encode("pass"))
                .build();
    }
}