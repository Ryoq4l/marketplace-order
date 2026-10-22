package com.adelok.marketplace_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class MarketplaceOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceOrderApplication.class, args);
	}

}
