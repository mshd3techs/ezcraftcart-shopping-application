package com.ezcraftcart.shipping.domain;


import lombok.Data;

@Data 
public class ShipmentRequest {

	private Long orderId;
	private String recipientName;
	private String recipientEmail;
	private String recipientPhone;
	private Address shippingAddress;
	private ShippingMethod shippingMethod;
}
