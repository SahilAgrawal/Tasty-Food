package com.tastyfoodpvtltd.ordermanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Notification;
import com.tastyfoodpvtltd.ordermanagementservice.domain.Order;
import com.tastyfoodpvtltd.ordermanagementservice.response.Restaurant;
import com.tastyfoodpvtltd.ordermanagementservice.service.OrderService;
import com.tastyfoodpvtltd.ordermanagementservice.service.RestaurantService;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    RestaurantService restaurantService;

    @PostMapping(path = "/")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);

    }

    @GetMapping(path = "/place/{oid}")
    public ResponseEntity<Order> placeOrder(@PathVariable String oid) {
        return new ResponseEntity<>(orderService.placeOrder(oid), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/view/{oid}")
    public ResponseEntity<Order> viewOrder(@PathVariable(name = "oid") String oid) {
        return new ResponseEntity<>(orderService.viewOrder(oid), HttpStatus.OK);
    }

    @GetMapping(path = "/notification/{oid}")
    public ResponseEntity<Notification> notification(@PathVariable(name = "oid") String oid) {
        return new ResponseEntity<>(orderService.notification(oid), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{oid}")
    public ResponseEntity<Order> updateOrder(@PathVariable(name = "oid") String oid, @RequestBody Order order) {
         return new ResponseEntity<>(orderService.updateOrder(order, oid),
                HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant/{rid}")
    public ResponseEntity<Restaurant> restaurantByIdFromOrderService(@PathVariable(name = "rid") String rid) {
        Restaurant restaurant = restaurantService.findRestaurantById(rid);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping(path = "/cancel/{oid}")
    public ResponseEntity<Order> cancelOrder(@PathVariable(name = "oid") String oid) {
        return new ResponseEntity<>(orderService.cancelOrder(oid), HttpStatus.OK);
    }
}