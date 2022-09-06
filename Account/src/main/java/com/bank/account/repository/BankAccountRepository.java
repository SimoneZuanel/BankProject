package com.bank.account.repository;

import com.bank.account.entity.BankAccount;
import com.bank.account.entity.BankAccountEnum;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    BankAccount findByNumberAccount(String numberAccount);

}
