package com.tastyfoodpvtltd.ordermanagementservice.domain;

import java.util.Date;

public class Notification {
    private String message;
    private Date createdAt;
    private boolean isRead;

    public Notification() {
    }

    public Notification(String message, Date createdAt, boolean isRead) {
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

}
