package pacman.plantmarket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Integer categoryId;
    private String categoryName;
    private String description;
    private Boolean isDeleted;

}
