package com.example.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.accountservice.model.SavingsAccount;

@Repository
public interface SavingsRepository extends JpaRepository<SavingsAccount, Long>  {
	List<SavingsAccount> findByCustomer_CustomerNumber(Long customerNumber);

}
