package pacman.plantmarket.dto;

import lombok.*;
import pacman.plantmarket.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer orderId;

    private String userId;
    private String username;

    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private String status;

    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private String notes;
    private String shippingAddress;
    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    private List<OrderDetailDTO> orderDetails;
    private PaymentDTO payment;
    private String paymentUrl;
}
