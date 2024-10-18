package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository repository;
    private final AlertMapper mapper;

    @Transactional
    @Override
    public AlertDTO addAlert(AlertAddDTO alertAddDTO) {
        Alert alert = mapper.mapAlertAddDtoToAlert(alertAddDTO);
        return mapper.mapAlertToAlertDto(repository.save(alert));
    }
}
