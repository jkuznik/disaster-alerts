package pl.ateam.disasteralerts.disasteralert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface DisasterRepository extends JpaRepository<Disaster, UUID> {
    Optional<Disaster> findById(UUID id);
    Optional<Disaster> findFirstByTypeAndLocationAndStatus(DisasterType type, String location, DisasterStatus status);
    List<Disaster> findAllByStatus(DisasterStatus status);
    List<Disaster> findAllByType(DisasterType type);
    List<Disaster> findAllByLocation(String location);
}
