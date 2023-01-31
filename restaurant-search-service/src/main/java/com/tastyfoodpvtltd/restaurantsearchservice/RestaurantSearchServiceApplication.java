package com.tastyfoodpvtltd.restaurantsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestaurantSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantSearchServiceApplication.class, args);
	}

}
