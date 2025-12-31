package pacman.plantmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pacman.plantmarket.dto.CategoryDTO;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.service.AdminService;
import pacman.plantmarket.service.CategoryService;
import pacman.plantmarket.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final CategoryService categoryService;

    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> getAllProduct(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                          @RequestParam(value = "selectedValue", required = false) Integer selectedValue) {
        Page<ProductDTO> productDTOS = adminService.getAllProduct(page, size, selectedValue);
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryDTOS);
    }
}
