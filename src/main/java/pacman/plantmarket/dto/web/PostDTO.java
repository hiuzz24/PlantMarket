package pacman.plantmarket.dto.web;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Integer postId;

    private String userId;
    private String username;

    private String content;
    private Boolean isDeleted;

    private List<PostImageDTO> images;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
