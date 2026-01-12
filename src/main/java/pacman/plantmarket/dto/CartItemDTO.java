package pacman.plantmarket.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private Integer cartItemId;
    private Integer productId;
    private Integer quantity;
    private Integer stockQuantity;
    private String productName;
    private String categoryName;
    private BigDecimal price;
    private String imageUrl;
}
