package pacman.plantmarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pacman.plantmarket.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByOrderCode(Long orderCode);

    Optional<Order> findByOrderId(Integer orderId);
}
