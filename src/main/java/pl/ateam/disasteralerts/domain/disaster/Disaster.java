package pl.ateam.disasteralerts.domain.disaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.domain.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disasters")
class Disaster extends EntityAudit {

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private DisasterType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Instant disasterStartTime;

    private Instant disasterEndTime;

    @Column(nullable = false)
    private DisasterStatus status;
}