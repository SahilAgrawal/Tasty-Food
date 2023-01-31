package com.tastyfoodpvtltd.ordermanagementservice.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.tastyfoodpvtltd.ordermanagementservice.domain.Notification;

@Service
public class NotificationService {

    public Notification setNotification(Notification notification, String oid, String msg) {
        notification.setCreatedAt(new Date());
        String message = String.format("ORDER %s for id #%s at %s", msg, oid,
                new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()));
        notification.setMessage(message);
        return notification;

    }

}
