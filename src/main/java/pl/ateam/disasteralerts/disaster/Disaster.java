package pl.ateam.disasteralerts.disaster;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;
import pl.ateam.disasteralerts.disaster.enums.DisasterType;
import pl.ateam.disasteralerts.util.EntityAudit;

import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING)
    private DisasterType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime disasterStartTime;

    private LocalDateTime disasterEndTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DisasterStatus status;
}
