package com.ecart.ecommerce.exception;

import com.ecart.ecommerce.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        // Customize the response entity as per your application's error handling requirements
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

