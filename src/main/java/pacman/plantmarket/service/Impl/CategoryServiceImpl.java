package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pacman.plantmarket.dto.CartDTO;
import pacman.plantmarket.dto.CategoryDTO;
import pacman.plantmarket.entity.Category;
import pacman.plantmarket.mapper.CategoryMapper;
import pacman.plantmarket.repository.CategoryRepository;
import pacman.plantmarket.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toDTO).toList();
    }


}
