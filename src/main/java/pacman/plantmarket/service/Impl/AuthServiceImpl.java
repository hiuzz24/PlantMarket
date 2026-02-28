package pacman.plantmarket.service.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pacman.plantmarket.config.SecurityUltis;
import pacman.plantmarket.dto.LoginRequestDTO;
import pacman.plantmarket.dto.LoginResponseDTO;
import pacman.plantmarket.dto.TokenPairDTO;
import pacman.plantmarket.entity.User;
import pacman.plantmarket.entity.UserToken;
import pacman.plantmarket.repository.UserRepository;
import pacman.plantmarket.repository.UserTokenRepository;
import pacman.plantmarket.security.JwtService;
import pacman.plantmarket.service.AuthService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    @Value("${google.client-id}")
    private String googleClientId;


    @Override
    public TokenPairDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveRefreshToken(user,refreshToken);
        return new  TokenPairDTO(accessToken,refreshToken);
    }

    @Transactional
    @Override
    public void logout(String refreshToken) {
        userTokenRepository.findByRefreshToken(refreshToken)
                .ifPresent(t -> {
                    t.setIsRevoked(true);
                    t.setExpiresAt(LocalDateTime.now());
                    userTokenRepository.save(t);
                });
    }

    @Override
    public void saveRefreshToken(User user, String refreshToken) {
        UserToken userToken = new UserToken();
        userToken.setRefreshToken(refreshToken);
        userToken.setUserId(user.getUserId());
        userToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        userToken.setIsRevoked(false);
        userTokenRepository.save(userToken);
    }

    @Override
    public LoginResponseDTO generateNewAccessToken(String refreshToken) {
        UserToken token = userTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refreshToken"));

        if(token.getIsRevoked() || token.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("RefreshToken is Revoked or Expired");
        }

        User user = token.getUser();
        UserDetails userDetails = SecurityUltis.toUserDetails(user);
        String accessToken =jwtService.generateAccessToken(userDetails);

        return new LoginResponseDTO(accessToken);
    }

    @Override
    public TokenPairDTO registerGoogleUser(String token) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);

            if (idToken == null) {
                throw new RuntimeException("Invalid Google Token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        User newUser = User.builder()
                                .userId(UUID.randomUUID().toString())
                                .email(email)
                                .username(email)
                                .password("GOOGLE_AUTH_NOPASS")
                                .fullName((String) payload.get("name"))
                                .avatarUrl((String) payload.get("picture"))
                                .roleId(2)
                                .status(true)
                                .build();
                        return userRepository.save(newUser);
                    });

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities("ROLE_USER")
                    .build();

            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(user);

            saveRefreshToken(user, refreshToken);

            return new TokenPairDTO(accessToken, refreshToken);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi xác thực Google: " + e.getMessage());
        }
    }
}
