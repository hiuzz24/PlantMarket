package pacman.plantmarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory_CategoryId(Integer categoryId, Pageable pageable);

    Optional<Product> findByProductId(Integer productId);
}
