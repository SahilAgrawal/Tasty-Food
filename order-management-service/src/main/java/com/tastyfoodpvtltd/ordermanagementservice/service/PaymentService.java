package com.tastyfoodpvtltd.ordermanagementservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Payment;
import com.tastyfoodpvtltd.ordermanagementservice.domain.PaymentMode;
import com.tastyfoodpvtltd.ordermanagementservice.domain.PaymentStatus;
import com.tastyfoodpvtltd.ordermanagementservice.exception.CustomException;
import com.tastyfoodpvtltd.ordermanagementservice.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment, String oid) {

        if (payment == null) {
            payment = new Payment(null, PaymentMode.CASH, PaymentStatus.INITIATED, new Date(), null, null, null);
            return paymentRepository.save(payment);
        }

        if (payment.getPaymentMode() == null) {
            payment.setPaymentMode(PaymentMode.CASH);
            payment.setPaymentStatus(PaymentStatus.INITIATED);
        } else if (payment.getPaymentMode() == PaymentMode.CARD || payment.getPaymentMode() == PaymentMode.UPI) {
            payment.setPaymentStatus(PaymentStatus.PROCESSING);
        } else {
            payment.setPaymentMode(PaymentMode.CASH);
            payment.setPaymentStatus(PaymentStatus.INITIATED);
        }
        payment.setOrderId(oid);
        // Date
        payment.setCreatedAt(new Date());
        payment.setCencelledAt(null);
        payment.setUpdatedAt(null);

        return paymentRepository.save(payment);
    }

    public Payment placePayment(Payment payment) {
        if (payment.getPaymentStatus() == PaymentStatus.INITIATED) {
            payment.setPaymentStatus(PaymentStatus.PROCESSING);
        } else if (payment.getPaymentStatus() == PaymentStatus.PROCESSING) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
        }

        payment.setUpdatedAt(new Date());
        return paymentRepository.save(payment);
    }

    public Payment setOrderId(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment cancelPayment(Payment payment) {
        payment.setCencelledAt(new Date());
        payment.setUpdatedAt(new Date());
        if (payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
            payment.setPaymentStatus(PaymentStatus.REFUND);
        } else {
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
        }
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Payment payment, Payment newPayment) {

        if (payment.getPaymentMode() != newPayment.getPaymentMode()) {
            if (newPayment.getPaymentMode() == PaymentMode.UPI || newPayment.getPaymentMode() == PaymentMode.CARD
                    || newPayment.getPaymentMode() == PaymentMode.CASH) {
                payment.setPaymentMode(newPayment.getPaymentMode());
            } else {
                throw new CustomException("Payment mode for order id #" + payment.getOrderId() + " is invalid",
                        HttpStatus.BAD_REQUEST);
            }
        }
        payment.setUpdatedAt(new Date());
        return payment;
    }
}
