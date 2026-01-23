package com.backend.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor  class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
}

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {

    	return ResponseEntity.badRequest()
    	        .body(new ErrorResponse(
    	                LocalDateTime.now(),
    	                400,
    	                ex.getMessage()
    	        ));

    }
}
