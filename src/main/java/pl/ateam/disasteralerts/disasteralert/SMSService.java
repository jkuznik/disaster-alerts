package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
class SMSService implements AlertListener{

    @Override
    public void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers) {

    }
}
