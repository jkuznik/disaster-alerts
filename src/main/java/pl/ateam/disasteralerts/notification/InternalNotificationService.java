package pl.ateam.disasteralerts.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.notification.dto.InternalNotificationDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class InternalNotificationService implements NotificationListener{

    private final InternalNotificationRepository internalNotificationRepository;
    private final InternalNotificationMapper internalNotificationMapper;

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, UserDTO interestedUser) {
        internalNotificationRepository.save(InternalNotification.builder()
                        .userId(interestedUser.id())
                        .disasterId(alertAddDTO.disasterId())
                        .alertDescription(alertAddDTO.description())
                        .alreadyRead(false)
                .build());
    }

    public List<InternalNotificationDTO> getAllIN(UUID userId) {
        return internalNotificationRepository.findAllByUserId(userId).stream()
                .map(internalNotificationMapper::toDto)
                .collect(Collectors.toList());
    }
}

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "internal_notifications")
class InternalNotification extends EntityAudit {

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID disasterId;

    private String alertDescription;

    private boolean alreadyRead;
}

@Repository
interface InternalNotificationRepository extends JpaRepository<InternalNotification, UUID> {
    Set<InternalNotification> findAllByUserId(UUID userId);
}

@Mapper(componentModel = "spring")
interface InternalNotificationMapper {
    InternalNotificationDTO toDto(InternalNotification internalNotification);
}


