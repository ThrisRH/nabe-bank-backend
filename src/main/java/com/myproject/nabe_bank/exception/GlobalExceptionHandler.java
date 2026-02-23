package com.myproject.nabe_bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ApiResponse<?> response = new ApiResponse<>(400, "ERROR", message, null);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(IllegalArgumentException exception) {
        ApiResponse<?> response = new ApiResponse<>(404, "ERROR", exception.getMessage(), null);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleServerError(Exception exception) {
        ApiResponse<?> reponse = new ApiResponse<>(500, "ERROR", "Internal Server Error", null);

        return ResponseEntity.status(500).body(reponse);
    }
}
