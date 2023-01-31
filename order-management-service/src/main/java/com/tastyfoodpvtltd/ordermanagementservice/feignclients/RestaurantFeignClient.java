package com.tastyfoodpvtltd.ordermanagementservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tastyfoodpvtltd.ordermanagementservice.response.Restaurant;

@FeignClient(value = "api-gateway")
public interface RestaurantFeignClient {

    @GetMapping(path = "/restaurant-search-service/restaurant/{rid}")
    public ResponseEntity<Restaurant> restaurantById(@PathVariable(name = "rid") String rid);

    @PutMapping(path = "/restaurant-search-service/restaurant")
    public ResponseEntity<Restaurant> restaurantUpdate(@RequestBody Restaurant restaurant);
}
