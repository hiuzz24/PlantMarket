package pacman.plantmarket.service;

import org.springframework.data.domain.Page;
import pacman.plantmarket.dto.ProductDTO;

public interface ProductService {
    Page<ProductDTO> getAllProduct(Integer page,Integer size);
}
