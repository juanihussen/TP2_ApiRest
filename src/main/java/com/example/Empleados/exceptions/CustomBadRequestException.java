package com.example.Empleados.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomBadRequestException extends RuntimeException {
    private final HttpStatus status;

    public CustomBadRequestException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}

