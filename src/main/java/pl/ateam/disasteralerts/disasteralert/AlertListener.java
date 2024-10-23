package pl.ateam.disasteralerts.disasteralert;

import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Set;

interface AlertListener {

    void addedAlert(AlertAddDTO alertAddDTO, Set<UserDTO> interestedUsers);
}
