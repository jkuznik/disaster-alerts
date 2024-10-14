package pl.ateam.disasteralerts.disasteralert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface DisasterRepository extends JpaRepository<Disaster, UUID> {
}
