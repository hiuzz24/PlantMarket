package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.CartItem;

import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    Optional<CartItem> findByCart_CartIdAndProductId(Integer CartId,Integer productId);
}
