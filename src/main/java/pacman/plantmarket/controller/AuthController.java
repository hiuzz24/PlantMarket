package pacman.plantmarket.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pacman.plantmarket.dto.LoginRequestDTO;
import pacman.plantmarket.dto.LoginResponseDTO;
import pacman.plantmarket.dto.TokenPairDTO;
import pacman.plantmarket.service.AuthService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request, HttpServletResponse response){
        TokenPairDTO tokenPairDTO = authService.login(request);
        ResponseCookie cookie = ResponseCookie
                .from("refreshToken",tokenPairDTO.getRefreshToken())
                .httpOnly(true)
                .path("/api/refreshToken")
                .maxAge(60 * 60 * 24 * 7)
                .sameSite("Strict")
                .secure(true)
                .build();

        response.addHeader("Set-Cookie",cookie.toString());

        return ResponseEntity.ok(new LoginResponseDTO(tokenPairDTO.getAccessToken()));
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String googleToken = request.get("token");
        log.info("Token nhận được: [" + googleToken + "]");
        TokenPairDTO tokenPairDTO = authService.registerGoogleUser(googleToken);

        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", tokenPairDTO.getRefreshToken())
                .httpOnly(true)
                .path("/api/refreshToken")
                .maxAge(60 * 60 * 24 * 7)
                .sameSite("Strict")
                .secure(true)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(new LoginResponseDTO(tokenPairDTO.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken",required = false) String refreshToken,
                                    HttpServletResponse response){
        log.info(refreshToken);
        if(refreshToken != null){
            authService.logout(refreshToken);
        }

        ResponseCookie cookie = ResponseCookie
                .from("refreshToken","")
                .path("/api/refreshToken")
                .secure(true)
                .maxAge(0)
                .httpOnly(true)
                .sameSite("Strict")
                .httpOnly(true)
                .build();

        response.addHeader("Set-Cookie",cookie.toString());

        return ResponseEntity.ok("Logout Successful");
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseDTO> generateNewAccessTokenToken(@CookieValue(value = "refreshToken",required = false) String refreshToken){
        LoginResponseDTO responseDTO = authService.generateNewAccessToken(refreshToken);
        return ResponseEntity.ok(responseDTO);
    }
}
