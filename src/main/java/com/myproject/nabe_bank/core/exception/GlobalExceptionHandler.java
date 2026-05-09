package com.myproject.nabe_bank.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myproject.nabe_bank.core.response.ResponseCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException exception) {
        ResponseCode responseCode = exception.getResponseCode();

        ApiResponse<?> response = new ApiResponse<>(responseCode.getCode(), responseCode.getMessage(), null);

        return ResponseEntity.badRequest().body(response);
    }
}
