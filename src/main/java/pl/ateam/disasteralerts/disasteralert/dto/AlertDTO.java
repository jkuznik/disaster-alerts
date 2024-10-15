package pl.ateam.disasteralerts.disasteralert.dto;

import java.time.Instant;
import java.util.UUID;
//TODO: moja propozycja zmiany pola Disaster disaster na UUID disasterId - wtedy można przenieść DTO do pakietu dto
public record AlertDTO(UUID id,
                UUID disasterId,
                String location,
                Instant creationDate) {
}
