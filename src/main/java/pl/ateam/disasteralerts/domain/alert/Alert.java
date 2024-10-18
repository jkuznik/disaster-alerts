package pl.ateam.disasteralerts.domain.alert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alerts")
class Alert extends EntityAudit {

    @Column(nullable = false)
    private UUID disasterId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private UUID alertUser;
}
