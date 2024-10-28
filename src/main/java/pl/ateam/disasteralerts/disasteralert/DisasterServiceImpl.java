package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddWebDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository repository;
    private final AlertService alertService;
    private final DisasterMapper mapper;

    @Transactional
    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO) {
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);

        return saveDisaster(disaster);
    }

    @Override
    public void addDisasterFromWeb(DisasterAddWebDTO disasterAddWebDTO) {
        Disaster disaster = mapper.mapDisasterAddWebDTOtoDisaster(disasterAddWebDTO);
        disaster.setSource("user");
        disaster.setStatus(DisasterStatus.ACTIVE);
        disaster.setDisasterStartTime(LocalDateTime.now());

        saveDisaster(disaster);
    }

    private DisasterDTO saveDisaster(Disaster disaster) {
        DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(repository.save(disaster));
        sendAlert(disasterDTO);
        return disasterDTO;
    }

    private void sendAlert(DisasterDTO disasterDTO) {
        AlertAddDTO alertAddDTO = new AlertAddDTO(
                UUID.randomUUID(),
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location(),
                LocalDateTime.now()) ;

        alertService.addAlert(alertAddDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(DisasterType type, String location) {
        return repository.findFirstByTypeAndLocationAndStatus(type, location, DisasterStatus.ACTIVE)
                .map(mapper::mapDisasterToDisasterDto);
    }
}
