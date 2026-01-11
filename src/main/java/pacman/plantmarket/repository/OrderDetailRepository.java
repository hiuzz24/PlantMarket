package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
}
