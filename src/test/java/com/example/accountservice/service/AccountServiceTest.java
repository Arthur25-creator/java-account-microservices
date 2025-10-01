package com.example.accountservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.dto.CustomerInquiryResponse;
import com.example.accountservice.exception.CustomerNotFoundException;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.AccountType;
import com.example.accountservice.model.SavingsAccount;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.SavingsRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private SavingsRepository savingsRepository;

    @Test
    void testCreateAccount_WithSavingsType_ShouldSaveSavings() {
        AccountDto request = new AccountDto();
        request.setCustomerName("John Doe");
        request.setCustomerMobile("09123456789");
        request.setCustomerEmail("john@example.com");
        request.setAddress1("Address1");
        request.setAddress2("Address2");
        request.setAccountType("S");

        Account mockAccount = new Account();
        mockAccount.setCustomerNumber(1L);
        mockAccount.setAccountType(AccountType.S);

        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        Account result = accountService.createAccount(request);

        assertEquals(1L, result.getCustomerNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(savingsRepository, times(1)).save(any(SavingsAccount.class));
    }

    @Test
    void testCreateAccount_WithInvalidType_ShouldThrowException() {
        AccountDto request = new AccountDto();
        request.setAccountType("X"); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(request);
        });

        assertEquals("Invalid account type. Must be 'S' or 'C'", exception.getMessage());
    }

    @Test
    void testGetCustomerByNumber_WhenFound_ShouldReturnResponse() {
        Long customerNumber = 1L;

        Account account = new Account();
        account.setCustomerNumber(customerNumber);
        account.setCustomerName("John");
        account.setCustomerMobile("09091234567");
        account.setCustomerEmail("john@example.com");
        account.setAddress1("Addr1");
        account.setAddress2("Addr2");
        account.setAccountType(AccountType.S);

        SavingsAccount savings = new SavingsAccount();
        savings.setAccountNumber(100L);
        savings.setAccountType(AccountType.S);
        savings.setAvailableBalance(500.0);
        savings.setCustomer(account);

        when(accountRepository.findByCustomerNumber(customerNumber)).thenReturn(Optional.of(account));
        when(savingsRepository.findByCustomer_CustomerNumber(customerNumber)).thenReturn(List.of(savings));

        CustomerInquiryResponse response = accountService.getCustomerByNumber(customerNumber);

        assertEquals(302, response.getTransactionStatusCode());
        assertEquals("Customer Account found", response.getTransactionStatusDescription());
        assertEquals(1, response.getSavings().size());
        assertEquals(100L, response.getSavings().get(0).getAccountNumber());
    }

    @Test
    void testGetCustomerByNumber_WhenNotFound_ShouldThrowException() {
        Long customerNumber = 999L;
        when(accountRepository.findByCustomerNumber(customerNumber)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(
            CustomerNotFoundException.class,
            () -> accountService.getCustomerByNumber(customerNumber)
        );

        assertEquals("Customer not found", exception.getMessage());
    }

}

