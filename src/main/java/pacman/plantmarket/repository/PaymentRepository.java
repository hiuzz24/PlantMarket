package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
