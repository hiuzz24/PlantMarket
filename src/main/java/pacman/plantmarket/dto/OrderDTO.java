package pacman.plantmarket.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer orderId;

    private String userId;
    private String username;

    private BigDecimal totalAmount;
    private String status;

    private String shippingAddress;
    private String paymentMethod;

    private LocalDateTime createdAt;

    private List<OrderDetailDTO> orderDetails;
    private PaymentDTO payment;
}
