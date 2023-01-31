package com.tastyfoodpvtltd.restaurantsearchservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tastyfoodpvtltd.restaurantsearchservice.domain.Restaurant;
import com.tastyfoodpvtltd.restaurantsearchservice.exception.CustomException;
import com.tastyfoodpvtltd.restaurantsearchservice.repository.RestaurantRepository;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository repository;

    public Restaurant findRestaurantById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new CustomException("Restaurant for id '" + id + "' not found.",
                        HttpStatus.NOT_FOUND));

    }

    public List<Restaurant> findRestaurantByName(String name) {
        return repository.findByName(name);
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public List<Restaurant> findRestaurantByCuisine(String cuisine) {
        return repository.findByCuisines(cuisine);
    }

    public List<Restaurant> findRestaurantByLocation(String location) {
        return repository.findByLocation(location);
    }

    public List<Restaurant> findAllRestaurants() {
        return repository.findAll();
    }

    public List<Restaurant> findRestaurantByBudget(double budget) {
        List<Restaurant> restaurants = repository.findByBudget(budget);
        return restaurants;
    }

    public List<Restaurant> findRestaurantByDistance(double distance, double latitude, double longitude) {
        List<Restaurant> restaurants = findAllRestaurants();
        List<Restaurant> finalRes = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            double resDis = findDistance(restaurant.getGeolocation().getLatitude(),
                    restaurant.getGeolocation().getLongitude(),
                    latitude, longitude);
            if (resDis <= distance) {
                finalRes.add(restaurant);
            }
        }
        restaurants.clear();
        return finalRes;
    }

    private double findDistance(double lat1, double long1, double lat2, double long2) {
        double theta = long1 - long2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        // KM
        dist = dist * 1.609344;
        System.out.println(dist);
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
