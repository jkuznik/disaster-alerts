package pl.ateam.disasteralerts.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.security.CustomPasswordEncoder;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.user.dto.UserRegisterDTO;
import pl.ateam.disasteralerts.user.dto.UserUpdateDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomPasswordEncoder customPasswordEncoder;

    boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    UserUpdateDTO findUserUpdateDto(UUID userId) {
        return userMapper.mapUserToUserUpdateDto(findById(userId));
    }

    Set<UserDTO> findAllByLocation(String location) {
        return userRepository.findAllByLocation(location).stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toSet());
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

    @Transactional
    public void updateUserEntity(UserUpdateDTO userUpdateDto, UUID userId) {

        User user = findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
        user.setLocation(userUpdateDto.location());
        user.setEmail(userUpdateDto.email());
        user.setFirstName(userUpdateDto.firstName());
        user.setLastName(userUpdateDto.lastName());
        user.setPhoneNumber(userUpdateDto.phoneNumber());

        userRepository.save(user);
    }
}