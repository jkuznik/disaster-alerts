package pl.ateam.disasteralerts.domain.alertUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserDTO;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlertUserServiceImpl implements AlertUserService {

    private final AlertUserRepository alertUserRepository;

    @Override
    public AlertUserDTO addAlertUser(AlertUserAddDTO alertUser) {
        return null;
    }

    @Override
    public List<AlertUserDTO> getAllByAlertId(UUID id) {
        return List.of();
    }

    @Override
    public List<AlertUserDTO> getAllByUserId(UUID userId) {
        return List.of();
    }
}
