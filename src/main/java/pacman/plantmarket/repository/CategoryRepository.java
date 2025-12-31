package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
