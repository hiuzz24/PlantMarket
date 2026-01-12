package pacman.plantmarket.service;

import pacman.plantmarket.dto.CartDTO;
import pacman.plantmarket.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategory();
}
