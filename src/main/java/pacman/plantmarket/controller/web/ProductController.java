package pacman.plantmarket.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pacman.plantmarket.dto.web.ProductDTO;
import pacman.plantmarket.service.web.ProductService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/market")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> getAllProduct(@RequestParam(value = "page",defaultValue = "1") int page,
                                                    @RequestParam(value = "size",defaultValue = "5") int size){

        Page<ProductDTO> productDTOS = productService.getAllProduct(page,size);
        log.info(productDTOS.getContent().toString());
        return ResponseEntity.ok(productDTOS);
    }
}
