package pacman.plantmarket.dto;

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
