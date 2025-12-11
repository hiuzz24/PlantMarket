package pacman.plantmarket.mapper.web;

import org.mapstruct.Mapper;
import pacman.plantmarket.dto.web.UserDTO;
import pacman.plantmarket.entity.web.User;

@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
