package pacman.plantmarket.dto.web;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImageDTO {
    private Integer imageId;
    private String imageUrl;
}
