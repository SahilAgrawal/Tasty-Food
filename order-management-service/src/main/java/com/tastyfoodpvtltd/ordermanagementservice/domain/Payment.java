package com.tastyfoodpvtltd.ordermanagementservice.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Payment")
public class Payment {

    @Id
    private String paymentId;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;
    private Date createdAt;
    private Date updatedAt;
    private Date cencelledAt;
    private String orderId;

    public Payment() {
    }

    public Payment(String paymentId, PaymentMode paymentMode, PaymentStatus paymentStatus, Date createdAt,
            Date updatedAt, Date cencelledAt, String orderId) {
        this.paymentId = paymentId;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cencelledAt = cencelledAt;
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public Date getCencelledAt() {
        return cencelledAt;
    }

    public void setCencelledAt(Date cencelledAt) {
        this.cencelledAt = cencelledAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
