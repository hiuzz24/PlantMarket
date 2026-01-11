package pacman.plantmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pacman.plantmarket.dto.OrderDTO;
import pacman.plantmarket.service.CartService;
import pacman.plantmarket.service.OrderService;
import pacman.plantmarket.service.PayOsService;
import vn.payos.model.webhooks.Webhook;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final PayOsService payOsService;
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO dto = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebHook(@RequestBody Webhook webhook){
        try {
            var verifiedData = payOsService.verifyWebhook(webhook);
            cartService.clearCart(verifiedData.getOrderCode());

            log.info("Webhook processed for order: {}", verifiedData.getOrderCode());
            return ResponseEntity.ok("Payment Successful");
        } catch (Exception e) {
            log.error("Webhook error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Webhook");
        }
    }

}
