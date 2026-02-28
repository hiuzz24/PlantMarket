package pacman.plantmarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pacman.plantmarket.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {

    Page<Product> findAllByIsDeletedFalse(Pageable pageable);

    Page<Product> findByCategory_CategoryId(Integer categoryId, Pageable pageable);

    Optional<Product> findByProductId(Integer productId);

    boolean existsByName(String name);

    boolean existsByCategory_CategoryId(Integer categoryId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p set p.stockQuantity = p.stockQuantity + :quantity " +
            "where p.productId = :id")
    void restoreStock(@Param("id") Integer productId, @Param("quantity") Integer quantity);
}
