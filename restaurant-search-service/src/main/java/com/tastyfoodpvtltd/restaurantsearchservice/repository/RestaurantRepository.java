package com.tastyfoodpvtltd.restaurantsearchservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tastyfoodpvtltd.restaurantsearchservice.domain.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    List<Restaurant> findByName(String name);

    List<Restaurant> findByCuisines(String cuisines);

    List<Restaurant> findByLocation(String location);

    @Query("{'budgetPerPerson':{ $lte : ?0}}")
    List<Restaurant> findByBudget(double budget);
}
