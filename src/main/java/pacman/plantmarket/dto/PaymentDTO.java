package pacman.plantmarket.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Integer paymentId;
    private Integer orderId;

    private BigDecimal amount;
    private String paymentStatus;
    private String paymentMethod;

    private String transactionCode;
    private String gatewayResponse;

    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
}
