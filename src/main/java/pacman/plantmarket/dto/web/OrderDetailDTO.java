package pacman.plantmarket.dto.web;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {

    private Integer orderDetailId;

    private Integer productId;
    private String productName;
    private String productImage;

    private Integer quantity;
    private BigDecimal price;

    private BigDecimal subTotal;
}
