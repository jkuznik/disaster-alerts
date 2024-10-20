package pl.ateam.disasteralerts.notification;

import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

interface AlertListener {

    void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers);
}
