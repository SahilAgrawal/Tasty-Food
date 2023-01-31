package com.tastyfoodpvtltd.ordermanagementservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Notification;
import com.tastyfoodpvtltd.ordermanagementservice.domain.Order;
import com.tastyfoodpvtltd.ordermanagementservice.domain.OrderStatus;
import com.tastyfoodpvtltd.ordermanagementservice.domain.Payment;
import com.tastyfoodpvtltd.ordermanagementservice.exception.CustomException;
import com.tastyfoodpvtltd.ordermanagementservice.repository.OrderRepository;
import com.tastyfoodpvtltd.ordermanagementservice.response.Restaurant;

@Service
public class OrderService {

    @Autowired
    PaymentService paymentService;
    ItemService itemService;
    NotificationService notificationService;
    OrderRepository orderRepository;
    MongoOperations mongoOperations;
    RestaurantService restaurantService;

    public OrderService(PaymentService paymentService, ItemService itemService, NotificationService notificationService,
            OrderRepository orderRepository, MongoOperations mongoOperations, RestaurantService restaurantService) {
        this.paymentService = paymentService;
        this.itemService = itemService;
        this.notificationService = notificationService;
        this.orderRepository = orderRepository;
        this.mongoOperations = mongoOperations;
        this.restaurantService = restaurantService;

    }

    public Order createOrder(Order order) {

        if (order.getItems() == null || order.getItems().size() == 0) {
            throw new CustomException("Items list can not be empty.", HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRid());
        itemService.createItem(restaurant.getItems(), order.getItems());
        // order.setItems(items);
        order.setTotalAmount(itemService.totalAmount(order.getItems()));

        Payment payment = order.getPayment();
        if (payment == null) {
            payment = new Payment();
        }

        // Got Payment ID and updating in order
        order.setPayment(paymentService.createPayment(payment, null));

        // Got order ID and updating in payment
        Order createdOrder = orderRepository.save(order);

        // Updating Orderid in payment
        payment.setOrderId(createdOrder.getId());
        payment = paymentService.setOrderId(payment);

        createdOrder.setPayment(payment);

        // Item

        createdOrder.setTotalAmount(itemService.totalAmount(order.getItems()));
        createdOrder.setStatus(OrderStatus.PLACED);

        // Date
        createdOrder.setCreatedAt(new Date());
        createdOrder.setCancelledAt(null);
        createdOrder.setDeleveredAt(null);
        createdOrder.setUpdatedAt(null);

        // Notification
        Notification notification = new Notification();
        notification.setRead(false);

        createdOrder
                .setNotification(notificationService.setNotification(notification, createdOrder.getId(), "CREATED"));
        order = null;
        restaurantService.updateRestaurantDetails(restaurant);
        return orderRepository.save(createdOrder);

    }

    public Order placeOrder(String oid) {
        Order order = viewOrder(oid);
        System.out.println(order.toString());
        if (order.getStatus() == OrderStatus.DELEIVERED || order.getStatus() == OrderStatus.CANCELLED
                || order == null) {
            throw new CustomException("Order for the given id #" + oid + " has been already delivered or cancelled.",
                    HttpStatus.BAD_REQUEST);

        } else if (order.getStatus() == OrderStatus.DISPATCHED) {
            order.setStatus(OrderStatus.DELEIVERED);
            order.setDeleveredAt(new Date());
            order.setPayment(paymentService.placePayment(order.getPayment()));
            order.setNotification(
                    notificationService.setNotification(order.getNotification(), order.getId(), "DELEIVERED"));
            return orderRepository.save(order);
        }

        order.setUpdatedAt(new Date());
        order.setStatus(OrderStatus.DISPATCHED);
        order.setPayment(paymentService.placePayment(order.getPayment()));
        order.setNotification(notificationService.setNotification(order.getNotification(), order.getId(), "PLACED"));

        return orderRepository.save(order);
    }

    public Order updateOrder(Order order, String oid) {

        // Checking that order for oid exists or not.
        Order oldOrder = viewOrder(oid);

        // STATUS
        if (oldOrder.getStatus() == OrderStatus.DELEIVERED || oldOrder.getStatus() == OrderStatus.CANCELLED
                || oldOrder.getStatus() == OrderStatus.DISPATCHED) {
            throw new CustomException("Order for given id #" + oid + " has already been " + oldOrder.getStatus(),
                    HttpStatus.BAD_REQUEST);
        }

        if (order.getItems() == null || order.getItems().size() == 0) {
            throw new CustomException("Items list can not be empty.", HttpStatus.BAD_REQUEST);
        }

        // Restaurant checking
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRid());
        order.setId(oldOrder.getId());
        itemService.updateRestaurantItems(restaurant.getItems(), oldOrder.getItems());
        itemService.createItem(restaurant.getItems(), order.getItems());
        order.setTotalAmount(itemService.totalAmount(order.getItems()));

        order.setStatus(oldOrder.getStatus());
        Payment newPayment = order.getPayment();
        if (newPayment == null) {
            order.setPayment(oldOrder.getPayment());
        } else {
            order.setPayment(paymentService.updatePayment(oldOrder.getPayment(), newPayment));
        }

        order.setUpdatedAt(new Date());
        order.setCreatedAt(oldOrder.getCreatedAt());
        order.setCancelledAt(oldOrder.getCancelledAt());
        order.setDeleveredAt(oldOrder.getDeleveredAt());
        Notification notification = notificationService.setNotification(oldOrder.getNotification(), oldOrder.getId(),
                "UPDATED");
        order.setNotification(notification);
        // order = null;
        oldOrder = null;
        restaurantService.updateRestaurantDetails(restaurant);
        return orderRepository.save(order);
    }

    public Order viewOrder(String oid) {
        Order order = orderRepository.findById(oid)
                .orElseThrow(() -> new CustomException("Order for the given id #" + oid + " not found",
                        HttpStatus.NOT_FOUND));
        System.out.println(order.toString());
        return order;

    }

    public Order cancelOrder(String oid) {
        Order order = viewOrder(oid);
        if (order == null) {
            return null;
        } else if (order.getStatus() == OrderStatus.DELEIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println("Already deleivered or cancelled");
            throw new CustomException("Order for the given id #" + oid + " has been already delivered or cancelled.",
                    HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRid());
        itemService.updateRestaurantItems(restaurant.getItems(), order.getItems());
        order.setUpdatedAt(new Date());
        order.setCancelledAt(new Date());
        order.setStatus(OrderStatus.CANCELLED);
        order.setPayment(paymentService.cancelPayment(order.getPayment()));
        order.setNotification(notificationService.setNotification(order.getNotification(), order.getId(), "CANCELLED"));

        restaurantService.updateRestaurantDetails(restaurant);
        return orderRepository.save(order);

    }

    public Notification notification(String oid) {
        Order order = viewOrder(oid);
        order.getNotification().setRead(true);
        return orderRepository.save(order).getNotification();
    }
}
