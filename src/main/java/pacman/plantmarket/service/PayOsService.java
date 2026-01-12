package pacman.plantmarket.service;

import pacman.plantmarket.entity.Order;
import vn.payos.model.webhooks.Webhook;
import vn.payos.model.webhooks.WebhookData;

public interface PayOsService {
    String createPaymentUrl(Order order);
    WebhookData verifyWebhook(Webhook webhook);
}
