package pacman.plantmarket.service;

import org.springframework.data.domain.Page;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.Order;
import pacman.plantmarket.entity.Product;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> getAllProduct(Integer page,Integer size);
    void reduceStock(Integer productId,Integer quantity);
    void restoreStock(Order order);
}
