package com.ezcraftcart.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.ezcraftcart")
@EnableCaching
@EnableJpaAuditing
public class CoreServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CoreServiceApplication.class, args);
    }
}
