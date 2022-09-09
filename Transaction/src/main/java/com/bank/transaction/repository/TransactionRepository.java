package com.bank.transaction.repository;

import com.bank.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByState(String state);

    List<Transaction> findFirst10ByDateAndIbanPayerAndStateOrderByIdDesc(String date, String iban, String state);


}
