package pl.ateam.disasteralerts.domain.alert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AlertRepository extends JpaRepository<Alert, UUID> {
}
