package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.airiskassessment.RiskAssessmentFacade;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        //TODO: trzeba dopracować promt do czata bo przy obecnym trudno osiągnąć wynik pozwalający na uznanie zdarzenia za prawdziwe
//        if (riskAssessment.assessRisk(disasterAddDTO)) {
//            disaster.setStatus(DisasterStatus.ACTIVE);
//            disasterRepository.save(disaster);
//            generateAlert(disaster.getId());
//        } else {
//            disaster.setStatus(DisasterStatus.FAKE);
//            disasterRepository.save(disaster);
//        }

        disaster.setStatus(DisasterStatus.ACTIVE);
        disasterRepository.save(disaster);
        generateAlert(disaster.getId());

        return mapper.mapDisasterToDisasterDto(disaster);
    }

    void generateAlert(UUID disasterId) {
        DisasterDTO disasterDTO = mapper.mapDisasterToDisasterDto(
                disasterRepository.findById(disasterId).orElseThrow(
                        () -> new IllegalArgumentException("Disaster not found")
                ));

        AlertAddDTO alertAddDTO = new AlertAddDTO(
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

    @Override
    public List<DisasterDTO> interestingDisasters(Optional<DisasterType> type, Optional<String> location) {
        //TODO: poniższa logika jest nie optymalna, do poprawy po zakończeniu konkursu

        List<DisasterDTO> interestedDisasters = disasterRepository.findAllByStatus(DisasterStatus.ACTIVE).stream()
                .map(mapper::mapDisasterToDisasterDto)
                .collect(Collectors.toList());

        if (type.isPresent()) {
            List<DisasterDTO> byType = disasterRepository.findAllByType(type.get()).stream()
                    .map(mapper::mapDisasterToDisasterDto)
                    .collect(Collectors.toList());

            interestedDisasters.retainAll(byType);
        }

        if (location.isPresent()) {
            List<DisasterDTO> byLocation = disasterRepository.findAllByLocation(location.get()).stream()
                    .map(mapper::mapDisasterToDisasterDto)
                    .collect(Collectors.toList());

            interestedDisasters.retainAll(byLocation);
        }

        return interestedDisasters;
    }
}
