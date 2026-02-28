package pacman.plantmarket.service;

import org.springframework.data.domain.Page;
import pacman.plantmarket.dto.OrderDTO;
import pacman.plantmarket.dto.ProductDTO;
import pacman.plantmarket.entity.OrderStatus;
import pacman.plantmarket.entity.Product;

import java.time.LocalDate;

public interface AdminService {
    Page<ProductDTO> getAllProduct(Integer page, Integer size, Integer selectedValue,String status);
    ProductDTO updateProduct(Integer productId, ProductDTO productDTO);
    ProductDTO toggleDeleteStatus(Integer productId);
    void createNew(ProductDTO productDTO);
    Page<OrderDTO> getAllOrder(Integer page, Integer size,String searchTerm, String status, LocalDate starDate,LocalDate endDate);
    void changeNewStatus(OrderStatus newStatus, Integer orderId);
    OrderDTO getOrderById(Integer orderId);

}
