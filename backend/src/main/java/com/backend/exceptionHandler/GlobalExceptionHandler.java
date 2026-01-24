package com.backend.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.Response.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

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
    
    @ExceptionHandler(com.backend.exception.ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(com.backend.exception.ResourceNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        404,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        List<String> message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toList();
//                .orElse("Validation failed");

        return ResponseEntity
                .badRequest()
                .body(message.toArray());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
    	System.out.println(ex);
        return ResponseEntity.status(500)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        500,
                        "An unexpected error occurred: " + ex.getMessage()
                ));
    }
}
