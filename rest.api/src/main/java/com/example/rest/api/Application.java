package com.example.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.example.rest.api")
@EnableDiscoveryClient
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
