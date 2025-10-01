package com.example.accountservice.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerNumber; 

    @Column(length = 50, nullable = false)
    private String customerName;

    @Column(length = 20, nullable = false)
    private String customerMobile;

    @Column(length = 50, nullable = false)
    private String customerEmail;

    @Column(length = 100, nullable = false)
    private String address1;

    @Column(length = 100)
    private String address2;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private AccountType accountType;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SavingsAccount> savingsAccounts;

}
