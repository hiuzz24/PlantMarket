package pacman.plantmarket.service;

import org.springframework.data.domain.Page;
import pacman.plantmarket.dto.ProductDTO;

public interface AdminService {
    Page<ProductDTO> getAllProduct(Integer page, Integer size, Integer selectedValue);

}
