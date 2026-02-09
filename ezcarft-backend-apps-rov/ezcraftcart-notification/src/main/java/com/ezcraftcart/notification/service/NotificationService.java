package com.ezcraftcart.notification.service;

import com.ezcraftcart.notification.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    private final JavaMailSender mailSender;
    
    @KafkaListener(topics = "order-events", groupId = "notification-service")
    public void handleOrderEvent(OrderEvent event) {
        log.info("Received order event: {}", event);
        
        switch (event.getStatus()) {
            case "CREATED" -> sendOrderConfirmationEmail(event);
            case "CONFIRMED" -> sendOrderConfirmedEmail(event);
            case "SHIPPED" -> sendOrderShippedEmail(event);
            case "DELIVERED" -> sendOrderDeliveredEmail(event);
            case "CANCELLED" -> sendOrderCancelledEmail(event);
            default -> log.warn("Unknown order status: {}", event.getStatus());
        }
    }
    
    public void sendOrderConfirmationEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Confirmation - EzCraftCart #" + event.getOrderId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Thank you for your order!\n\n" +
                "Order Number: %d\n" +
                "Total Amount: \$%.2f\n\n" +
                "We will send you another email when your order is shipped.\n\n" +
                "Best regards,\n" +
                "EzCraftCart Team",
                event.getCustomerName(),
                event.getOrderId(),
                event.getTotalAmount()
            ));
            
            mailSender.send(message);
            log.info("Order confirmation email sent to {}", event.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send order confirmation email", e);
        }
    }
    
    public void sendOrderConfirmedEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Payment Confirmed - EzCraftCart #" + event.getOrderId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Your payment has been confirmed!\n\n" +
                "Order Number: %d\n" +
                "Amount Paid: \$%.2f\n\n" +
                "Your order is being prepared for shipment.\n\n" +
                "Best regards,\n" +
                "EzCraftCart Team",
                event.getCustomerName(),
                event.getOrderId(),
                event.getTotalAmount()
            ));
            
            mailSender.send(message);
            log.info("Payment confirmation email sent to {}", event.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send payment confirmation email", e);
        }
    }
    
    public void sendOrderShippedEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Shipped - EzCraftCart #" + event.getOrderId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Great news! Your order has been shipped.\n\n" +
                "Order Number: %d\n" +
                "Tracking Number: %s\n\n" +
                "You can track your shipment using the tracking number above.\n\n" +
                "Best regards,\n" +
                "EzCraftCart Team",
                event.getCustomerName(),
                event.getOrderId(),
                event.getAdditionalData().getOrDefault("trackingNumber", "N/A")
            ));
            
            mailSender.send(message);
            log.info("Shipment notification email sent to {}", event.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send shipment notification email", e);
        }
    }
    
    public void sendOrderDeliveredEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Delivered - EzCraftCart #" + event.getOrderId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Your order has been delivered!\n\n" +
                "Order Number: %d\n\n" +
                "We hope you enjoy your purchase. Please leave us a review!\n\n" +
                "Best regards,\n" +
                "EzCraftCart Team",
                event.getCustomerName(),
                event.getOrderId()
            ));
            
            mailSender.send(message);
            log.info("Delivery notification email sent to {}", event.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send delivery notification email", e);
        }
    }
    
    public void sendOrderCancelledEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Cancelled - EzCraftCart #" + event.getOrderId());
            message.setText(String.format(
                "Dear %s,\n\n" +
                "Your order has been cancelled.\n\n" +
                "Order Number: %d\n" +
                "Amount: \$%.2f\n\n" +
                "If you have any questions, please contact our support team.\n\n" +
                "Best regards,\n" +
                "EzCraftCart Team",
                event.getCustomerName(),
                event.getOrderId(),
                event.getTotalAmount()
            ));
            
            mailSender.send(message);
            log.info("Cancellation notification email sent to {}", event.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send cancellation notification email", e);
        }
    }
}
