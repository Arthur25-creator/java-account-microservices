package com.example.accountservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
	private int transactionStatusCode;
    private String transactionStatusDescription;

    public ErrorResponse(int code, String description) {
        this.transactionStatusCode = code;
        this.transactionStatusDescription = description;
    }

}
