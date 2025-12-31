package pacman.plantmarket.service;

import pacman.plantmarket.dto.CartDTO;
import pacman.plantmarket.dto.CartItemDTO;

public interface CartService {
    CartDTO getCartByUser();
    boolean addToCart(CartItemDTO cartItemDTO);
    void updateCart(CartItemDTO cartItemDTO);
}
