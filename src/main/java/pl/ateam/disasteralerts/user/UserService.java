package pl.ateam.disasteralerts.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    boolean existsById(UUID userId) {
        return repository.existsById(userId);
    }

    boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    List<User> getAllUsers() {
        return repository.findAll();
    }

    public void save(User user) {
        repository.save(user);
    }

    public void saveAll(Collection<User> users) {
        repository.saveAll(users);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteAll(Collection<User> users) {
        repository.deleteAll(users);
    }

    public long userCount() {
        return repository.count();
    }

}