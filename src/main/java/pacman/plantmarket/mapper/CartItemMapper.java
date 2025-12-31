package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pacman.plantmarket.dto.CartItemDTO;
import pacman.plantmarket.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDTO toDTO(CartItem cartItem);

    CartItem toEntity(CartItemDTO cartItemDTO);
}
