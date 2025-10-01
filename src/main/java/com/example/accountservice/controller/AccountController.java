package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.dto.ApiResponse;
import com.example.accountservice.dto.CustomerInquiryResponse;
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
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDto accountDto) {
    	Account savedAccount = accountService.createAccount(accountDto);

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
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

}
