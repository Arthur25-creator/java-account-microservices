package com.example.accountservice.service;


import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.dto.CustomerInquiryResponse;
import com.example.accountservice.dto.SavingsDto;
import com.example.accountservice.exception.CustomerNotFoundException;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.AccountType;
import com.example.accountservice.model.SavingsAccount;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.SavingsRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private SavingsRepository savingsRepository;

    @Transactional
    public Account createAccount(AccountDto accountDto) {

        AccountType type;
        try {
            type = AccountType.valueOf(accountDto.getAccountType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account type. Must be 'S' or 'C'");
        }

        Account account = new Account();
        account.setCustomerName(accountDto.getCustomerName());
        account.setCustomerMobile(accountDto.getCustomerMobile());
        account.setCustomerEmail(accountDto.getCustomerEmail());
        account.setAddress1(accountDto.getAddress1());
        account.setAddress2(accountDto.getAddress2());
        account.setAccountType(type);
        
        Account savedAccount = accountRepository.save(account);

        if (type == AccountType.S) {
            SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setAccountType(AccountType.S);
            savingsAccount.setAvailableBalance(0.0);
            savingsAccount.setCustomer(savedAccount);
            savingsRepository.save(savingsAccount);
        }

        return savedAccount;
    }
    
    public CustomerInquiryResponse getCustomerByNumber(Long customerNumber) {

        Optional<Account> optAccount = accountRepository.findByCustomerNumber(customerNumber);

        if (optAccount.isEmpty()) {
           throw new CustomerNotFoundException("Customer not found");
        }

        Account account = optAccount.get();

        List<SavingsAccount> savingsAccounts = savingsRepository.findByCustomer_CustomerNumber(customerNumber);
        List<SavingsDto> savingsDTOs = savingsAccounts.stream()
            .map(s -> new SavingsDto(
                s.getAccountNumber(),
                s.getAccountType().name(),
                s.getAvailableBalance()
            ))
            .collect(Collectors.toList());

        return new CustomerInquiryResponse(
            account.getCustomerNumber(),
            account.getCustomerName(),
            account.getCustomerMobile(),
            account.getCustomerEmail(),
            account.getAddress1(),
            account.getAddress2(),
            savingsDTOs,
            302,
            "Customer Account found"
        );
    }

}
