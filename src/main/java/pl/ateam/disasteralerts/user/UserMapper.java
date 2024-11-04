package pl.ateam.disasteralerts.user;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.user.dto.UserDTO;
import pl.ateam.disasteralerts.user.dto.UserLoginDTO;
import pl.ateam.disasteralerts.user.dto.UserRegisterDTO;
import pl.ateam.disasteralerts.user.dto.UserUpdateDTO;

@Mapper(componentModel = "spring")
interface UserMapper {

    UserDTO mapUserToUserDTO(User user);
    User mapUserDTOToUser(UserDTO userDTO);
    UserLoginDTO mapUserToUserLoginDTO(User user);
    User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO);

    UserUpdateDTO mapUserToUserUpdateDto(User user);
}
