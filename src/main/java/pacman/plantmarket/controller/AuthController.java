package pacman.plantmarket.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pacman.plantmarket.dto.LoginRequestDTO;
import pacman.plantmarket.dto.LoginResponseDTO;
import pacman.plantmarket.dto.TokenPairDTO;
import pacman.plantmarket.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseDTO> generateNewAccessTokenToken(@CookieValue(value = "refreshToken") String refreshToken){
        LoginResponseDTO responseDTO = authService.generateNewAccessToken(refreshToken);
        return ResponseEntity.ok(responseDTO);
    }
}
