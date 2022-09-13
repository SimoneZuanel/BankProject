package com.bank.account.repository;

import com.bank.account.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    @Query(value = "SELECT bankAccount.numberAccount FROM BankAccount bankAccount")
    List<String> findNumberAccounts();

    BankAccount findByNumberAccount(String numberAccount);

    BankAccount findByIban(String iban);

    List<BankAccount> findByUsername(String username);

}
