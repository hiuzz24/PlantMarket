package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pacman.plantmarket.dto.CartDTO;
import pacman.plantmarket.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "userId",source = "user.userId")
    CartDTO toDTO(Cart cart);
    Cart toEntity(CartDTO cartDTO);
}
