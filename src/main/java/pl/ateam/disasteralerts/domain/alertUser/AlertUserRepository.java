package pl.ateam.disasteralerts.domain.alertUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlertUserRepository extends JpaRepository<AlertUser, UUID> {
}
