package pl.ateam.disasteralerts.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.util.UUID;

@Service
class InternalNotificationService implements NotificationListener{

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, UserDTO interestedUser) {

    }
}

@Entity
class InternalNotification extends EntityAudit {

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID alertId;

    private String alertDescription;

    private boolean alreadyRead;
}




