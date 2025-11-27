package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import pacman.plantmarket.dto.UserDTO;
import pacman.plantmarket.entity.User;

@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
