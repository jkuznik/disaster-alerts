package pl.ateam.disasteralerts.disasteralert;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.disasteralert.dto.AlertUserAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertUserDTO;

@Mapper(componentModel = "spring")
public interface AlertUserMapper {
    AlertUserDTO mapAlertUserToAlertUserDTO(AlertUser alertUser);
    AlertUser mapAlertUserDTOToAlertUser(AlertUserDTO alertUserDTO);
    AlertUser mapAlertUserAddDtoToAlertUser(AlertUserAddDTO alertUserAddDTODTO);
}
