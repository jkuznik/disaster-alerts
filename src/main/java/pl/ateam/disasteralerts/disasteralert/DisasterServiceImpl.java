package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository disasterRepository;
    private final AlertService alertService;
    private final DisasterMapper mapper;

    private final String USER_AS_DISASTER_SOURCE = "user";

    @Transactional
    @Override
    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO) {
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);
        disaster.setSource(USER_AS_DISASTER_SOURCE);
        disaster.setStatus(DisasterStatus.ACTIVE);
        disasterRepository.save(disaster);

        generateAlert(disaster.getId());

        return mapper.mapDisasterToDisasterDto(disaster);
    }

    void generateAlert(UUID disasterId) {
        DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(
                disasterRepository.findById(disasterId).get());

        AlertAddDTO alertAddDTO = new AlertAddDTO(
                UUID.randomUUID(),
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location()) ;

        alertService.addAlert(alertAddDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(DisasterType type, String location) {
        return disasterRepository.findFirstByTypeAndLocationAndStatus(type, location, DisasterStatus.ACTIVE)
                .map(mapper::mapDisasterToDisasterDto);
    }
}
