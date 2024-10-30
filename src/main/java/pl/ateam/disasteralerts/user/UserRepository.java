package pl.ateam.disasteralerts.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    Set<User> findAllByLocation(String location);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}