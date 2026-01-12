package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findByOrderCode(Long orderCode);
}
