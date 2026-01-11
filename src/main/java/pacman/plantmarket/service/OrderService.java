package pacman.plantmarket.service;

import jakarta.servlet.http.HttpServletRequest;
import pacman.plantmarket.dto.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
}
