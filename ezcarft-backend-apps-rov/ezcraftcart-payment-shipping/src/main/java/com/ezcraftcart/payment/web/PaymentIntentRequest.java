package com.ezcraftcart.payment.web;

import lombok.Data;

@Data
public class PaymentIntentRequest {

    private String currency = "usd";
    private String customerId;
    private String receiptEmail;
}
