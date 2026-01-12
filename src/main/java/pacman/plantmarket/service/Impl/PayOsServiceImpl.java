package pacman.plantmarket.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pacman.plantmarket.entity.Order;
import pacman.plantmarket.exception.PaymentException;
import pacman.plantmarket.repository.OrderRepository;
import pacman.plantmarket.service.OrderService;
import pacman.plantmarket.service.PayOsService;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.webhooks.Webhook;
import vn.payos.model.webhooks.WebhookData;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayOsServiceImpl implements PayOsService {
    private final OrderRepository orderRepository;
    private final PayOS payOS;


    @Transactional
    @Override
    public String createPaymentUrl(Order order) {
        try {
            Long amount = order.getTotalAmount().longValue();
            String description = "thanh toan don hang " + order.getOrderId();
            Long expiredAt = (System.currentTimeMillis() / 1000) + (15*60);
            String cancelUrl = "http://localhost:5173/cancel";
            String successUrl = "http://localhost:5173/success";

            CreatePaymentLinkRequest paymentLinkRequest = CreatePaymentLinkRequest.builder()
                    .orderCode(order.getOrderCode())
                    .amount(amount)
                    .description(description)
                    .expiredAt(expiredAt)
                    .buyerAddress(order.getShippingAddress())
                    .buyerEmail(order.getEmailAddress())
                    .buyerName(order.getFullName())
                    .buyerPhone(order.getPhoneNumber())
                    .cancelUrl(cancelUrl)
                    .returnUrl(successUrl)
                    .build();

            var paymentUrl = payOS.paymentRequests().create(paymentLinkRequest);
            log.info(paymentUrl.getCheckoutUrl());
            return paymentUrl.getCheckoutUrl();
        } catch (Exception e) {
            log.error("PayOS Integration Error: ", e);
            throw new PaymentException("Error to create payment: " + e.getMessage());
        }
    }

    @Override
    public WebhookData verifyWebhook(Webhook webhook) {
        try{
            return payOS.webhooks().verify(webhook);
        }catch (Exception e){
            log.error("Webhook verification failed", e);
            throw new PaymentException("Invalid webhook data");
        }
    }
}
