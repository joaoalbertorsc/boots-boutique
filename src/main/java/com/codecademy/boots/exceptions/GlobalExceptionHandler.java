package com.codecademy.boots.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QueryNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleQueryNotSupportedException(QueryNotSupportedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Map<String, String>> handleNotImplementedException(NotImplementedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Not Implemented");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
    }
}
