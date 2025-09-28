package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountInfo;
import com.example.accountservice.dto.ApiResponse;
import com.example.accountservice.dto.CustomerInquiryResponse;
import com.example.accountservice.dto.ErrorResponse;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountInfo accountInfo) {
    	Account savedAccount = accountService.createAccount(accountInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(
                savedAccount.getCustomerNumber(),
                201,
                "Customer account created"
            )
        );
    }
    
    @GetMapping("/{customerNumber}")
    public ResponseEntity<?> getCustomer(@PathVariable Long customerNumber) {
        CustomerInquiryResponse response = accountService.getCustomerByNumber(customerNumber);

        if (response.getTransactionStatusCode() == 401) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(401, "Customer not found"));
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

}
