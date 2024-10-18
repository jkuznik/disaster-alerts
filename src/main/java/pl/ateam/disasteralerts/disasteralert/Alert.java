package pl.ateam.disasteralerts.disasteralert;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alerts")
class Alert extends EntityAudit {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Disaster disaster;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant creationDate;
}
