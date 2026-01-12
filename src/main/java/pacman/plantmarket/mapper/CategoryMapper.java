package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import pacman.plantmarket.dto.CategoryDTO;
import pacman.plantmarket.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
