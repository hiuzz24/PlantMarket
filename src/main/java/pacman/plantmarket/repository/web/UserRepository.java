package pacman.plantmarket.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.web.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
