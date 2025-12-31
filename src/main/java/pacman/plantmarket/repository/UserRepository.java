package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
