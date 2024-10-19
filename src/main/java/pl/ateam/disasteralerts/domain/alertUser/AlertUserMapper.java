package pl.ateam.disasteralerts.domain.alertUser;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.domain.alertUser.dto.AlertUserDTO;

@Mapper(componentModel = "spring")
public interface AlertUserMapper {
    AlertUserDTO mapAlertUserToAlertUserDTO(AlertUser alertUser);
    AlertUser mapAlertUserDTOToAlertUser(AlertUserDTO alertUserDTO);
}
