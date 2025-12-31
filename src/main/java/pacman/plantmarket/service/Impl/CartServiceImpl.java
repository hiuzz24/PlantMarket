package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pacman.plantmarket.config.SecurityUltis;
import pacman.plantmarket.dto.CartDTO;
import pacman.plantmarket.dto.CartItemDTO;
import pacman.plantmarket.entity.Cart;
import pacman.plantmarket.entity.CartItem;
import pacman.plantmarket.entity.Product;
import pacman.plantmarket.entity.User;
import pacman.plantmarket.mapper.CartItemMapper;
import pacman.plantmarket.mapper.CartMapper;
import pacman.plantmarket.repository.CartItemRepository;
import pacman.plantmarket.repository.CartRepository;
import pacman.plantmarket.repository.UserRepository;
import pacman.plantmarket.service.CartService;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartDTO getCartByUser() {
        String email = SecurityUltis.getCurrentUser();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));

        Cart cart = cartRepository.findCartByUser_UserId(user.getUserId())
                .orElseThrow(() -> new NoSuchElementException("not found cart"));

        List<CartItemDTO> cartItemDTOS = cart.getCartItems().stream()
                .map(item -> CartItemDTO.builder()
                        .cartItemId(item.getCartItemId())
                        .productId(item.getProductId())
                        .productName(item.getProduct().getName())
                        .categoryName(item.getProduct().getCategory().getCategoryName())
                        .quantity(item.getQuantity())
                        .stockQuantity(item.getProduct().getStockQuantity())
                        .price(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .imageUrl(item.getProduct().getImageUrl())
                        .build())
                .toList();

        CartDTO cartDTO = cartMapper.toDTO(cart);
        cartDTO.setCartItems(cartItemDTOS);

        BigDecimal totalPrice = cartItemDTOS.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cartDTO.setTotalPrice(totalPrice);

        return cartDTO;
    }

    @Transactional
    @Override
    public boolean addToCart(CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);

        String email = SecurityUltis.getCurrentUser();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));

        Cart cart = cartRepository.findCartByUser_UserId(user.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem1 = cartItemRepository.findByCart_CartIdAndProductId(cart.getCartId(),cartItem.getProductId())
                .map(item -> {
                    item.setQuantity(cartItem.getQuantity() + item.getQuantity());
                    return item;
                })
                .orElseGet(() -> {
                   CartItem newCartItem = new CartItem();
                   newCartItem.setQuantity(cartItem.getQuantity());
                   newCartItem.setProductId(cartItem.getProductId());
                   newCartItem.setCartId(cart.getCartId());
                   return newCartItem;
                });

        cartItemRepository.save(cartItem1);

        return true;
    }

    @Override
    public void updateCart(CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        String email = SecurityUltis.getCurrentUser();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));

        Cart cart = cartRepository.findCartByUser_UserId(user.getUserId())
                .orElseThrow(() -> new NoSuchElementException("not found cart"));

        CartItem cartItem1 = cartItemRepository.findByCart_CartIdAndProductId(cart.getCartId(), cartItem.getProductId())
                .orElseThrow(() -> new NoSuchElementException("not found cart_item"));

        Product product = cartItem1.getProduct();
        if(product.getStockQuantity() < cartItem.getQuantity()){
            throw new IllegalArgumentException("Not enough stock");
        }

        cartItem1.setQuantity(cartItem.getQuantity());

        cartItemRepository.save(cartItem1);
    }
}
