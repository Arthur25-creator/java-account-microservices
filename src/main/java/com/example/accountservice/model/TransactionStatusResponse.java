package com.example.accountservice.model;

import lombok.Data;

@Data
public class TransactionStatusResponse {
    private int transactionStatusCode;
    private String transactionStatusDescription;

    public TransactionStatusResponse(int code, String description) {
        this.transactionStatusCode = code;
        this.transactionStatusDescription = description;
    }
}