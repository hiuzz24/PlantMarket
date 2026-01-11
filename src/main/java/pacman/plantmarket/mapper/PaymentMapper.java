package pacman.plantmarket.mapper;

import org.mapstruct.Mapper;
import pacman.plantmarket.dto.PaymentDTO;
import pacman.plantmarket.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDTO(Payment payment);
    Payment toEntity(PaymentDTO paymentDTO);
}
