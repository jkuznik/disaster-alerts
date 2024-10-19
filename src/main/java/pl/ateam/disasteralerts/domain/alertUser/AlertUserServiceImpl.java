package pl.ateam.disasteralerts.domain.alertUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertUserServiceImpl implements AlertUserService {

    private final AlertUserRepository alertUserRepository;


}
