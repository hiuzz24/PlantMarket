package pacman.plantmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.service.CategoryService;
import pacman.plantmarket.service.ProductService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getAllProduct(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                            @RequestParam(value = "size",defaultValue = "5") Integer size){
        Page<ProductDTO> productDTOS = productService.getAllProduct(page,size);
        return ResponseEntity.ok(productDTOS);
    }

}
