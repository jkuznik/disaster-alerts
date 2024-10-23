package pl.ateam.disasteralerts.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.security.CustomPasswordEncoder;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.user.dto.UserRegisterDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomPasswordEncoder customPasswordEncoder;

    boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    List<UserDTO> findAllByLocation(String location) {
        return null;
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika o adresie email: " + email));
    }

    public UserDTO findByEmail(String email) {
        User user = findUserByEmail(email);
        return userMapper.mapUserToUserDTO(user);
    }

    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void save(UserRegisterDTO userRegisterDTO) {
        User user = userMapper.mapUserRegisterDTOToUser(userRegisterDTO);
        user.setRole(Role.ROLE_USER);
        user.setPassword(customPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void saveAll(Collection<User> users) {
        userRepository.saveAll(users);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteAll(Collection<User> users) {
        userRepository.deleteAll(users);
    }

    public long userCount() {
        return userRepository.count();
    }

}