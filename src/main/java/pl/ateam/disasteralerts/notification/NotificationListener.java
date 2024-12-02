package pl.ateam.disasteralerts.notification;

import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.user.dto.UserDTO;

interface NotificationListener {

    void addedAlert(AlertAddDTO alertAddDTO, UserDTO interestedUser);
}
