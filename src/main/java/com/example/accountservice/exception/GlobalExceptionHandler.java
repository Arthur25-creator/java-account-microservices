package com.example.accountservice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.accountservice.model.TransactionStatusResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TransactionStatusResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().get(0); 
        String errorMessage = error.getDefaultMessage(); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new TransactionStatusResponse(400, errorMessage)
        );
    }
}
