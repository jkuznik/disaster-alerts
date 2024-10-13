package pl.ateam.disasteralerts.disasteralert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alerts")
class Alert {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private DisasterType type;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private DisasterStatus status;
}
