package com.example.Empleados.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends RuntimeException {
    private final HttpStatus status;

    public ConflictException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }
}





