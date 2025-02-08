package com.reactive.playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.reactive.playground.${sec}")
@EnableR2dbcRepositories(basePackages = "com.reactive.playground.${sec}")
public class WebfluxPlaygroundApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxPlaygroundApplication.class, args);
	}
}
