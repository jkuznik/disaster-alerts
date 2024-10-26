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

    @Override
    public void addDisasterFromWeb(DisasterAddWebDTO disasterAddWebDTO) {
        Disaster disaster = mapper.mapDisasterAddWebDTOtoDisaster(disasterAddWebDTO);
        disaster.setSource("user");
        disaster.setStatus(DisasterStatus.ACTIVE);
        disaster.setDisasterStartTime(LocalDateTime.now());
        repository.save(disaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(DisasterType type, String location) {
        return repository.findFirstByTypeAndLocationAndStatus(type, location, DisasterStatus.ACTIVE)
                .map(mapper::mapDisasterToDisasterDto);
    }
}
