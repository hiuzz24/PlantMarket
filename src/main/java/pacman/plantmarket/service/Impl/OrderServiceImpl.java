package pacman.plantmarket.service.Impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pacman.plantmarket.config.SecurityUltis;
import pacman.plantmarket.dto.OrderDTO;
import pacman.plantmarket.entity.*;
import pacman.plantmarket.mapper.OrderMapper;
import pacman.plantmarket.mapper.PaymentMapper;
import pacman.plantmarket.repository.*;
import pacman.plantmarket.service.CartService;
import pacman.plantmarket.service.OrderService;
import pacman.plantmarket.service.PayOsService;
import pacman.plantmarket.service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PayOsService payOsService;
    private final CartService cartService;
    private static final BigDecimal SHIPPING_FEE = new BigDecimal("30000");

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String email = SecurityUltis.getCurrentUser();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));

        Cart cart = cartRepository.findCartByUser_UserId(user.getUserId())
                .orElseThrow(() -> new NoSuchElementException("not found cart"));

        Order order = new Order();
        Long orderCode = System.currentTimeMillis() / 1000;
        order.setOrderCode(orderCode);
        order.setStatus(OrderStatus.PENDING);
        order.setUserId(user.getUserId());
        order.setShippingFee(SHIPPING_FEE);
        orderMapper.updateEntityFromDTO(orderDTO, order);


        BigDecimal subTotal = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAmount = subTotal.add(SHIPPING_FEE);

        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        List<OrderDetail> orderDetails = cart.getCartItems().stream().map(
                        item -> OrderDetail.builder()
                                .orderId(order.getOrderId())
                                .price(item.getProduct().getPrice())
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .build()
                )
                .collect(Collectors.toList());
        orderDetailRepository.saveAll(orderDetails);

        order.setOrderDetails(orderDetails);

        OrderDTO responseDto = orderMapper.toDTO(order);

        Payment payment = Payment.builder()
                .amount(totalAmount)
                .paymentMethod(order.getPaymentMethod())
                .orderId(order.getOrderId())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        responseDto.setPayment(paymentMapper.toDTO(payment));
        paymentRepository.save(payment);


        if(order.getPaymentMethod().equals(PaymentMethod.PAYOS)){
            String paymentUrl = payOsService.createPaymentUrl(order);
            log.info(paymentUrl);
            responseDto.setPaymentUrl(paymentUrl);
        }else if(order.getPaymentMethod().equals(PaymentMethod.COD)){
            order.setStatus(OrderStatus.PENDING);
            order.getPayment().setPaymentStatus(PaymentStatus.PENDING);
            cartService.clearCart(orderCode);
        }
        return responseDto;
    }


}
