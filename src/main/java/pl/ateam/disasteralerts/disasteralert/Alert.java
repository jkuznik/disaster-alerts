package pl.ateam.disasteralerts.disasteralert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
