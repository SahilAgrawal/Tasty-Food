package com.tastyfoodpvtltd.ordermanagementservice.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tastyfoodpvtltd.ordermanagementservice.exception.CustomException;
import com.tastyfoodpvtltd.ordermanagementservice.feignclients.RestaurantFeignClient;
import com.tastyfoodpvtltd.ordermanagementservice.response.Restaurant;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class RestaurantService {

    org.slf4j.Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    RestaurantFeignClient restaurantFeignClient;

    private int count = 1;

    // Circuit Breaker for RestaurantFindById
    @CircuitBreaker(name = "restaurantsearchservice", fallbackMethod = "fallbackFindRestaurantById")
    public Restaurant findRestaurantById(String id) {
        logger.info("Count: " + count);
        ++count;
        Restaurant restaurant = restaurantFeignClient.restaurantById(id).getBody();

        return restaurant;
    }

    public Restaurant fallbackFindRestaurantById(String id, Throwable th) {
        // return new
        logger.error("Error: " + th);
        throw new CustomException("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CircuitBreaker(name = "restaurantsearchservice", fallbackMethod = "fallbackUpdateRestaurantDetails")
    public void updateRestaurantDetails(Restaurant restaurant) {
        logger.info("Updating Restaurant Details from Restaurant Service & Feign Client");
        System.out.println(restaurant.toString());
        restaurant = restaurantFeignClient.restaurantUpdate(restaurant).getBody();
        System.out.println(restaurant.toString());
        logger.info("Updation Done");
        return;

    }

    public void fallbackUpdateRestaurantDetails(Restaurant restaurant, Throwable th) {
        logger.error("Error: " + th);
        throw new CustomException("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
