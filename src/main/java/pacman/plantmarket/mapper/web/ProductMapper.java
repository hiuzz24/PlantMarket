package pacman.plantmarket.mapper.web;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pacman.plantmarket.dto.web.ProductDTO;
import pacman.plantmarket.entity.web.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.categoryId",target = "categoryId")
    @Mapping(source = "category.categoryName",target = "categoryName")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
