package pl.ateam.disasteralerts.domain.alertUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlertUserRepository extends JpaRepository<AlertUser, UUID> {
    Optional<AlertUser> findAllByAlertId(UUID alertId);
    Optional<AlertUser> findAllByUserId(UUID userId);
}