package com.tastyfoodpvtltd.ordermanagementservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String>{
    
}
