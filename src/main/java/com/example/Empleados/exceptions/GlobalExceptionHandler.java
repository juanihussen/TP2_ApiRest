package com.example.Empleados.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", ex.getStatus().value());
        responseBody.put("error", ex.getStatus().getReasonPhrase());
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException1(CustomBadRequestException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", ex.getStatus().value());
        responseBody.put("error", ex.getStatus().getReasonPhrase());
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", ex.getStatus().value());
        responseBody.put("error", ex.getStatus().getReasonPhrase());
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

}


