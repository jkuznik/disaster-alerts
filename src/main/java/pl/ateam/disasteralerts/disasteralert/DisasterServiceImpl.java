package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository repository;
    private final AlertService alertService;
    private final DisasterMapper mapper;

    @Transactional
    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO){
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);
        DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(repository.save(disaster));

        AlertAddDTO alertAddDTO = new AlertAddDTO(
                UUID.randomUUID(),
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location(),
                LocalDateTime.now());

        alertService.addAlert(alertAddDTO);
        return disasterDTO;
    }
}
