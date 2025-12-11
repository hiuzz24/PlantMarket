package pacman.plantmarket.service.web;

import org.springframework.data.domain.Page;
import pacman.plantmarket.dto.web.ProductDTO;

public interface ProductService {
    Page<ProductDTO> getAllProduct(int page,int size);
}
