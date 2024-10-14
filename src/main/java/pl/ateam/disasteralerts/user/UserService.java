package pl.ateam.disasteralerts.user;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void save(User user) {
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