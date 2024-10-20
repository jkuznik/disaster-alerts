package pl.ateam.disasteralerts.alertUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.alertUser.dto.AlertUserDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertUserServiceImpl implements AlertUserService {

    private final AlertUserRepository repository;
    private final AlertUserMapper mapper;

    @Transactional
    @Override
    public AlertUserDTO addAlertUser(AlertUserAddDTO alertUserAddDTO) {
        AlertUser alertUser = mapper.mapAlertUserAddDtoToAlertUser(alertUserAddDTO);

        return mapper.mapAlertUserToAlertUserDTO(repository.save(alertUser));
    }

    @Override
    public List<AlertUserDTO> getAllByAlertId(UUID alertId) {
        return repository.findAllByAlertId(alertId).stream()
                .map(mapper::mapAlertUserToAlertUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertUserDTO> getAllByUsername(String username) {
        return repository.findAllByUsername(username).stream()
                .map(mapper::mapAlertUserToAlertUserDTO)
                .collect(Collectors.toList());
    }
}
