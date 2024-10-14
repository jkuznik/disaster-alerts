package pl.ateam.disasteralerts.disasteralert;

import org.springframework.stereotype.Service;

@Service
class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }
}
