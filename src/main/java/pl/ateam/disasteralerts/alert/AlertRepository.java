package pl.ateam.disasteralerts.alert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AlertRepository extends JpaRepository<Alert, UUID> {
}
