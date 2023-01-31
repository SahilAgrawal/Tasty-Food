package com.tastyfoodpvtltd.restaurantsearchservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tastyfoodpvtltd.restaurantsearchservice.domain.Item;
import com.tastyfoodpvtltd.restaurantsearchservice.domain.Restaurant;
import com.tastyfoodpvtltd.restaurantsearchservice.service.RestaurantService;

@RestController
@RequestMapping(path = "/restaurant")
public class RestaurantController {

    Logger logger = LoggerFactory.getLogger(RestaurantController.class.getName());

    @Autowired
    RestaurantService restaurantService;

    @GetMapping(path = "/name", params = "name")
    public ResponseEntity<List<Restaurant>> restaurantByName(@RequestParam(name = "name") String name) {
        logger.info("Restaurant- Find by Name: " + name);
        List<Restaurant> restaurants = restaurantService.findRestaurantByName(name);
        if (restaurants.size() == 0) {

            logger.info("Restaurant for name: " + name + " not found.");
            return new ResponseEntity<>(restaurants, HttpStatus.NOT_FOUND);
        }
        logger.info("Restaurant for name: " + name + " found.");
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping(path = "/cuisine", params = "cuisine")
    public ResponseEntity<List<Restaurant>> restaurantByCuisine(@RequestParam(name = "cuisine") String cuisine) {
        List<Restaurant> restaurants = restaurantService.findRestaurantByCuisine(cuisine);
        if (restaurants.size() == 0) {
            return new ResponseEntity<>(restaurants, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping(path = "/location", params = "location")
    public ResponseEntity<List<Restaurant>> restaurantByLocation(@RequestParam(name = "location") String location) {
        List<Restaurant> restaurants = restaurantService.findRestaurantByLocation(location);
        if (restaurants.size() == 0) {
            return new ResponseEntity<>(restaurants, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping(path = "/distance")
    public ResponseEntity<List<Restaurant>> restaurantByDistance(@RequestParam(name = "distance") double distance,
            @RequestParam(name = "latitude") double latitude,
            @RequestParam(name = "longitude") double longitude) {
        List<Restaurant> restaurants = restaurantService.findRestaurantByDistance(distance,
                latitude, longitude);
        if (restaurants.size() == 0) {
            return new ResponseEntity<>(restaurants, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping(path = "/budget")
    public ResponseEntity<List<Restaurant>> restaurantByBudget(@RequestParam(name = "person") int person,
            @RequestParam(name = "totalBudget") double totalBudget) {

        double budget = totalBudget / person;
        List<Restaurant> restaurants = restaurantService.findRestaurantByBudget(budget);
        if (restaurants.size() == 0) {
            return new ResponseEntity<>(restaurants, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping(path = "/{rid}/item")
    public ResponseEntity<List<Item>> menuitemByRestaurantId(@PathVariable(name = "rid") String rid) {
        Restaurant restaurant = restaurantService.findRestaurantById(rid);
        if (restaurant == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant.getItems(), HttpStatus.OK);
    }

    @GetMapping(path = "/{rid}")
    public ResponseEntity<Restaurant> restaurantById(@PathVariable(name = "rid") String rid) {
        Restaurant restaurant = restaurantService.findRestaurantById(rid);
        if (restaurant == null) {
            return new ResponseEntity<>(restaurant, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant) {
        return new ResponseEntity<>(restaurantService.saveRestaurant(restaurant), HttpStatus.CREATED);
    }

    @PutMapping(path = "")
    public ResponseEntity<Restaurant> restaurantUpdate(@RequestBody Restaurant restaurant) {
        System.out.println(restaurant.toString());
        return new ResponseEntity<>(restaurantService.saveRestaurant(restaurant), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Restaurant>> allRestaurants() {
        return new ResponseEntity<>(restaurantService.findAllRestaurants(), HttpStatus.OK);
    }
}
