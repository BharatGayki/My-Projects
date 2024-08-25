package com.ecommerce.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ecommerce.library")
@EnableJpaRepositories(basePackages = "com.ecommerce.library.repo")
@EntityScan(basePackages = "com.ecommerce.library.model")
public class EcommerceWebSpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceWebSpringBootProjectApplication.class, args);
	}
}
