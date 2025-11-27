package pacman.plantmarket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private Integer cartItemId;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;

    private String productName;
    private Double price;
    private String imageUrl;
}
