package com.tastyfoodpvtltd.restaurantsearchservice.exception;

import org.springframework.http.HttpStatus;

public class CustomMessage {
    private final String message;

    private final HttpStatus httpStatus;
    private final java.util.Date time;

    public CustomMessage(String message, HttpStatus httpStatus, java.util.Date time) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public java.util.Date getTime() {
        return time;
    }

}
