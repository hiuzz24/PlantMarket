package pacman.plantmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pacman.plantmarket.entity.UserToken;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken,Integer> {
    Optional<UserToken> findByRefreshToken(String refreshToken);
}
