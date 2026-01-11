package pacman.plantmarket.service;

import pacman.plantmarket.dto.LoginRequestDTO;
import pacman.plantmarket.dto.LoginResponseDTO;
import pacman.plantmarket.dto.TokenPairDTO;
import pacman.plantmarket.entity.User;

public interface AuthService {
    TokenPairDTO login(LoginRequestDTO request);
    void logout(String refreshToken);
    void saveRefreshToken(User user,String refreshToken);
    LoginResponseDTO generateNewAccessToken(String refreshToken);
}
