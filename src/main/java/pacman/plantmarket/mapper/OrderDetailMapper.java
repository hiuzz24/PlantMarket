package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pacman.plantmarket.dto.OrderDetailDTO;
import pacman.plantmarket.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(source = "orderDetail.product.name", target = "productName")
    @Mapping(source = "orderDetail.product.imageUrl", target = "productImage")
    OrderDetailDTO toDTO(OrderDetail orderDetail);

    OrderDetail toEntity(OrderDetailDTO orderDetailDTO);
}
