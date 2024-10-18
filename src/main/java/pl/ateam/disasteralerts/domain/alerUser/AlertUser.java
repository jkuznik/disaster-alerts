package pl.ateam.disasteralerts.domain.alerUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "alerts_users")
public class AlertUser extends EntityAudit {

    @Column(nullable = false)
    private UUID alertId;

    @Column(nullable = false)
    private UUID userId;
}
