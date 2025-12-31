package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.categoryId",target = "categoryId")
    @Mapping(source = "category.categoryName",target = "categoryName")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
