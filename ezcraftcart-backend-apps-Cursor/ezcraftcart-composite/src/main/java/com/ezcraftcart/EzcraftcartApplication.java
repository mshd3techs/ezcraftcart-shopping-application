package com.ezcraftcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {
    "com.ezcraftcart.catalog",
    "com.ezcraftcart.identity",
    "com.ezcraftcart.cart",
    "com.ezcraftcart.order",
    "com.ezcraftcart.cms",
    "com.ezcraftcart.payment",
    "com.ezcraftcart.common"
})
@EnableCaching
public class EzcraftcartApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzcraftcartApplication.class, args);
    }
}
