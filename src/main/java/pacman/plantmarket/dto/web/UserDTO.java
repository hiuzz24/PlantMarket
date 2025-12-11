package pacman.plantmarket.dto.web;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String userId;
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String phone;
    private String address;

    private Integer roleId;
    private String roleName;

    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
