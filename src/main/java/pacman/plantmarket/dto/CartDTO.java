package pacman.plantmarket.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private Integer cartId;
    private String userId;
    private LocalDateTime createdAt;

    private List<CartItemDTO> items;

    private Double totalPrice;
}
