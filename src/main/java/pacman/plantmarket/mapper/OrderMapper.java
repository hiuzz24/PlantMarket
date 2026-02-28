package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pacman.plantmarket.dto.OrderDTO;
import pacman.plantmarket.entity.Order;

@Mapper(componentModel = "spring",uses = {OrderDetailMapper.class})
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "orderDetails",ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "shippingFee",ignore = true)
    void updateEntityFromDTO(OrderDTO orderDTO, @MappingTarget Order order);
}
