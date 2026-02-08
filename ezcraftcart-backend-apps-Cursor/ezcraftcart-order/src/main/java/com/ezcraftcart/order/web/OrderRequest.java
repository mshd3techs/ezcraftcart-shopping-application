package com.ezcraftcart.order.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank
    private String cartId;

    private String userId;  // optional - from JWT if authenticated
}
