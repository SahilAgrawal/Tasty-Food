package com.tastyfoodpvtltd.ordermanagementservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Item;
import com.tastyfoodpvtltd.ordermanagementservice.exception.CustomException;

@Service
public class ItemService {

    @Autowired
    RestaurantService restaurantService;

    public double totalAmount(List<Item> items) {
        double total = 0;
        for (Item item : items) {
            total += item.getAmount();
        }
        return total;
    }

    public void createItem(List<com.tastyfoodpvtltd.ordermanagementservice.response.Item> restaurantItems,
            List<Item> orderItems) {

        boolean flag = true;
        for (int i = 0; i < orderItems.size(); i++) {
            for (int j = 0; j < restaurantItems.size(); j++) {
                if (orderItems.get(i).getItem().equalsIgnoreCase(restaurantItems.get(j).getName())) {
                    if (orderItems.get(i).getQuantity() <= restaurantItems.get(j).getInStock()
                            && orderItems.get(i).getQuantity() > 0) {
                        restaurantItems.get(j)
                                .setInStock(restaurantItems.get(j).getInStock() - orderItems.get(i).getQuantity());
                    } else {
                        throw new CustomException("Either Quantity for order item is 0 or higher than available stock",
                                HttpStatus.BAD_REQUEST);
                    }
                    orderItems.get(i).setPrice(restaurantItems.get(j).getPrice());
                    orderItems.get(i).setAmount(orderItems.get(i).getPrice() * orderItems.get(i).getQuantity());
                    flag = false;

                }
            }
            if (flag) {
                throw new CustomException(
                        "Ordered item is not present in restaurant menu." + orderItems.get(i).getItem(),
                        HttpStatus.BAD_REQUEST);
            }
        }

    }

    public void updateRestaurantItems(
            List<com.tastyfoodpvtltd.ordermanagementservice.response.Item> restaurantItems,
            List<Item> oldOrderItems) {

        boolean flag = true;
        for (int i = 0; i < oldOrderItems.size(); i++) {
            for (int j = 0; j < restaurantItems.size(); j++) {
                if (oldOrderItems.get(i).getItem().equalsIgnoreCase(restaurantItems.get(j).getName())) {
                    restaurantItems.get(j)
                            .setInStock(restaurantItems.get(j).getInStock() + oldOrderItems.get(i).getQuantity());
                    flag = false;
                }
            }
            if (flag) {
                throw new CustomException(
                        "Ordered item is not present in restaurant menu." + oldOrderItems.get(i).getItem(),
                        HttpStatus.BAD_REQUEST);
            }
        }

    }
}