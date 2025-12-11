package pacman.plantmarket.dto.web;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenDTO {

    private Integer tokenId;
    private String userId;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private Boolean isRevoked;
}
