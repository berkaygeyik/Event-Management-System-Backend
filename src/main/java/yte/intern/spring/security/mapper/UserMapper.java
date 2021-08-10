package yte.intern.spring.security.mapper;

import org.mapstruct.Mapper;
import yte.intern.spring.security.dto.UserDTO;
import yte.intern.spring.security.entity.Users;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapToDto(Users users);

    Users mapToEntity(UserDTO userDTO);

    List<UserDTO> mapToDto(List<Users> usersList);

    List<Users> mapToEntity(List<UserDTO> userDTOList);
}
