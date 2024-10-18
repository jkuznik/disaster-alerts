package pl.ateam.disasteralerts.domain.user;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.domain.user.dto.UserDTO;
import pl.ateam.disasteralerts.domain.user.dto.UserLoginDTO;

@Mapper(componentModel = "spring")
interface UserMapper {

    UserDTO mapUserToUserDTO(User user);
    User mapUserDTOToUser(UserDTO userDTO);
    UserLoginDTO mapUserToUserLoginDTO(User user);

}
