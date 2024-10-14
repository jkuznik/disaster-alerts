package pl.ateam.disasteralerts.disasteralert;

import java.time.Instant;
import java.util.UUID;
//TODO: w obecnej postaci nie można przenieść tej klasy/rekordu do innego pakietu,
public record AlertDTO(UUID id,
                Disaster disaster,
                String location,
                Instant creationDate) {
}
