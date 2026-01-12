package pacman.plantmarket.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Integer commentId;
    private Integer postId;
    private String userId;

    private Integer parentCommentId;
    private String content;
    private LocalDateTime createdAt;

    private List<CommentDTO> replies;
}
