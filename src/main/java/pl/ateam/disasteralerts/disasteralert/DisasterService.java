package pl.ateam.disasteralerts.disasteralert;

import org.springframework.stereotype.Service;

@Service
class DisasterService {

    private final DisasterRepository disasterRepository;

    public DisasterService(DisasterRepository disasterRepository) {
        this.disasterRepository = disasterRepository;
    }
}
