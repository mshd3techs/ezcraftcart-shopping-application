package com.ezcraftcart.payment.web;

import com.ezcraftcart.payment.service.PaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks/stripe")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookController {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Value("${stripe.webhook-secret:}")
    private String webhookSecret;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {

        if (webhookSecret == null || webhookSecret.isBlank()) {
            log.warn("Stripe webhook secret not configured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            Webhook.constructEvent(payload, signature, webhookSecret);
        } catch (SignatureVerificationException e) {
            log.warn("Invalid webhook signature: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            JsonNode event = objectMapper.readTree(payload);
            String type = event.path("type").asText();
            JsonNode data = event.path("data").path("object");

            switch (type) {
                case "payment_intent.succeeded" -> {
                    String paymentIntentId = data.path("id").asText();
                    paymentService.handlePaymentSucceeded(paymentIntentId);
                }
                case "payment_intent.payment_failed" -> {
                    String paymentIntentId = data.path("id").asText();
                    String error = data.path("last_payment_error").path("message").asText("");
                    paymentService.handlePaymentFailed(paymentIntentId, error);
                }
                default -> log.debug("Unhandled webhook event type: {}", type);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing webhook: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
