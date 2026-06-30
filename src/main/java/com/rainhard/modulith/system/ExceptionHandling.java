package com.rainhard.modulith.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

public class ExceptionHandling {

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
            ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null, LocalDateTime.now());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // HTTP 404 Not Found
        }
    }

}
