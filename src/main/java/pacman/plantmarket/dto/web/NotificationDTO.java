package pacman.plantmarket.dto.web;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Integer notificationId;
    private String userId;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;

}
