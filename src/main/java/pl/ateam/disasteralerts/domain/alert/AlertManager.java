package pl.ateam.disasteralerts.domain.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
class AlertManager {

    private final List<AlertListener> alertListeners = new ArrayList<>();
    void createAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {
        alertListeners.forEach(alertListener -> alertListener.addedAlert(alertAddDTO, interestedUsers));
    }

    void addAlertListener(AlertListener alertListener) {
        alertListeners.add(alertListener);
    }

    void removeAlertListener(AlertListener alertListener) {
        alertListeners.remove(alertListener);
    }
}
