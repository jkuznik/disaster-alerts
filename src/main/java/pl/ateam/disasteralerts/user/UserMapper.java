package pl.ateam.disasteralerts.user;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO mapUserToDTO(User user);
    User mapUserDTOToUser(UserDTO userDTO);

}
