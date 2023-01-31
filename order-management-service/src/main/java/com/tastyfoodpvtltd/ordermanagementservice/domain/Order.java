package com.tastyfoodpvtltd.ordermanagementservice.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Order")
public class Order {
    @Id
    private String id;
    private String rid;
    private List<Item> items;
    private double totalAmount;
    private OrderStatus status;
    private Payment payment;
    private Notification notification;

    private Date createdAt;
    private Date updatedAt;
    private Date deleveredAt;
    private Date cancelledAt;

    public Order() {
    }

    public Order(String id, String rid, List<Item> items, double totalAmount, OrderStatus status, Payment payment,
            Notification notification, Date createdAt, Date updatedAt, Date deleveredAt, Date cancelledAt) {
        this.id = id;
        this.rid = rid;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.payment = payment;
        this.notification = notification;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleveredAt = deleveredAt;
        this.cancelledAt = cancelledAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeleveredAt() {
        return deleveredAt;
    }

    public void setDeleveredAt(Date deleveredAt) {
        this.deleveredAt = deleveredAt;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

}
