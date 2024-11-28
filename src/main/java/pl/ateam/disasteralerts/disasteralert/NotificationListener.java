package pl.ateam.disasteralerts.disasteralert;

import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

interface NotificationListener {

    void addedAlert(AlertAddDTO alertAddDTO, UserDTO interestedUser);
}
