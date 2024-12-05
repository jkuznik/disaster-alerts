package pl.ateam.disasteralerts.disaster;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.airiskassessment.RiskAssessmentFacade;
import pl.ateam.disasteralerts.alert.AlertFacade;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;
import pl.ateam.disasteralerts.util.enums.DisasterStatus;
import pl.ateam.disasteralerts.util.enums.DisasterType;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "prototype")
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository disasterRepository;
    private final AlertFacade alertFacade;
    private final DisasterMapper mapper;
    private final RiskAssessmentFacade riskAssessment;

    @Transactional
    @Override
    public DisasterDTO createDisaster(DisasterAddDTO disasterAddDTO, String source) {
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);
        disaster.setSource(source);
        //TODO: trzeba dopracować promt do czata bo przy obecnym trudno osiągnąć wynik pozwalający na uznanie zdarzenia za prawdziwe
        // edit: tymczasowo można po prostu zmniejszyć wymagany wynik zwracany przez AI aby zdarzenie zakwalifikować jako ACTIVE
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
                disasterDTO.id(),
                disasterDTO.description(),
                disasterDTO.location());

        alertFacade.createAlert(alertAddDTO);
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

    @Override
    public Map<String, Integer> inLocationDisastersAmount() {
        List<Disaster> allActiveDisasters = disasterRepository.findAllByStatus(DisasterStatus.ACTIVE);

        return allActiveDisasters.stream()
                .collect(Collectors.groupingBy(
                        Disaster::getLocation,
                        Collectors.summingInt(e -> 1)
                ));
    }
}
