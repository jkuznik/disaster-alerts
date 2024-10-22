package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;

@Service
@RequiredArgsConstructor
class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    @Override
    public AlertDTO addAlert(AlertAddDTO alertAddDTO) {
        return null;
    }
}
