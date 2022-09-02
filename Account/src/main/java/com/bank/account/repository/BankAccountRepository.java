package com.bank.account.repository;

import com.bank.account.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

}
