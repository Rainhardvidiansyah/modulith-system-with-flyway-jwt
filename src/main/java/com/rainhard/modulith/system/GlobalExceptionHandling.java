package com.rainhard.modulith.system;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandling {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        System.out.println(">>> GlobalExceptionHandler tangkap: " + ex.getMessage());
        return build(HttpStatus.NOT_FOUND, request, "DATA_NOT_FOUND", message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
     String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
    return build(HttpStatus.BAD_REQUEST, request, "VALIDATION_ERROR", message);
}

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            NoHandlerFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, request, "NOT_FOUND", "Resource tidak ditemukan");
    }


    private ResponseEntity<ApiResponse<Void>> build(HttpStatus status, HttpServletRequest request,
                                                    String code, String message) {
        return ResponseEntity.status(status).body(
                ApiResponse.<Void>error(status.value(), request.getRequestURI(), code, message)
        );
    }
}
