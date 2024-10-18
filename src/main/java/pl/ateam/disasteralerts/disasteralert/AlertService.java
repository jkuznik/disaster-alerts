package pl.ateam.disasteralerts.disasteralert;

import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;

public interface AlertService {
    AlertDTO addAlert(AlertAddDTO alertAddDTO);
}
