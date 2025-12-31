package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.Cart;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findCartByUser_UserId(String userId);
}
