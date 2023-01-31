package com.tastyfoodpvtltd.ordermanagementservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Order;


public interface OrderRepository extends MongoRepository<Order, String> {

}
