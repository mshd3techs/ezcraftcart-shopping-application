package com.ezcraftcart.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.api-key:}")
    private String apiKey;

    @PostConstruct
    void init() {
        if (apiKey != null && !apiKey.isBlank()) {
            Stripe.apiKey = apiKey;
        }
    }

    public String createPaymentIntent(long amountInCents, String currency, String orderId,
                                      String customerId, String receiptEmail) throws StripeException {
        PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency(currency.toLowerCase())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build())
                .putMetadata("orderId", orderId);

        if (receiptEmail != null && !receiptEmail.isBlank()) {
            builder.setReceiptEmail(receiptEmail);
        }
        if (customerId != null && !customerId.isBlank()) {
            builder.setCustomer(customerId);
        }

        PaymentIntent intent = PaymentIntent.create(builder.build());
        return intent.getClientSecret();
    }

    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        return PaymentIntent.retrieve(paymentIntentId);
    }

    public boolean verifyWebhookSignature(String payload, String signature, String webhookSecret) {
        try {
            com.stripe.net.Webhook.constructEvent(payload, signature, webhookSecret);
            return true;
        } catch (Exception e) {
            log.warn("Webhook signature verification failed: {}", e.getMessage());
            return false;
        }
    }
}
