package com.stripe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SbStripeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbStripeApplication.class, args);
	}

}
