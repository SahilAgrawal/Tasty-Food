package com.tastyfoodpvtltd.restaurantsearchservice.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<CustomMessage> handleCustomException(CustomException e) {
        return new ResponseEntity<CustomMessage>(new CustomMessage(e.getMessage(), e.getHttpStatus(), new Date()),
                e.getHttpStatus());
    }
}
