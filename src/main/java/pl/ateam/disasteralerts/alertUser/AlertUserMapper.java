package pl.ateam.disasteralerts.alertUser;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.alertUser.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.alertUser.dto.AlertUserDTO;

@Mapper(componentModel = "spring")
public interface AlertUserMapper {
    AlertUserDTO mapAlertUserToAlertUserDTO(AlertUser alertUser);
    AlertUser mapAlertUserDTOToAlertUser(AlertUserDTO alertUserDTO);
    AlertUser mapAlertUserAddDtoToAlertUser(AlertUserAddDTO alertUserAddDTODTO);
}
