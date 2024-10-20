package pl.ateam.disasteralerts.domain.alert;

import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;

import java.util.Set;

interface AlertListener {

    void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers);
}
