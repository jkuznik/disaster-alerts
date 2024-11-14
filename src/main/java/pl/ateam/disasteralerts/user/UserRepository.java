package pl.ateam.disasteralerts.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Set<User> findAllByLocation(String location);
    boolean existsByEmail(String email);
}