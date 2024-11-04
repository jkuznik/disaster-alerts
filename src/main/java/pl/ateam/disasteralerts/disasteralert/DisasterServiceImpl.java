package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.airiskassessment.RiskAssessmentFacade;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.util.Optional;
import java.util.UUID;

@Service(value = "prototype")
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository disasterRepository;
    private final AlertService alertService;
    private final DisasterMapper mapper;
    private final RiskAssessmentFacade riskAssessment;

    @Transactional
    @Override
    public DisasterDTO createDisaster(DisasterAddDTO disasterAddDTO, String source) {
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);
        disaster.setSource(source);
        if (riskAssessment.assessRisk(disasterAddDTO)) {
            disaster.setStatus(DisasterStatus.ACTIVE);
            disasterRepository.save(disaster);
            generateAlert(disaster.getId());
        } else {
            disaster.setStatus(DisasterStatus.FAKE);
            disasterRepository.save(disaster);
        }

        return mapper.mapDisasterToDisasterDto(disaster);
    }

    void generateAlert(UUID disasterId) {
        DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(
                disasterRepository.findById(disasterId).orElseThrow(
                        () -> new IllegalArgumentException("Disaster not found")
                ));

        AlertAddDTO alertAddDTO = new AlertAddDTO(
                UUID.randomUUID(),
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location());

        alertService.createAlert(alertAddDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisasterDTO> getActiveDisasterForTypeAndLocation(DisasterType type, String location) {
        return disasterRepository.findFirstByTypeAndLocationAndStatus(type, location, DisasterStatus.ACTIVE)
                .map(mapper::mapDisasterToDisasterDto);
    }
}
