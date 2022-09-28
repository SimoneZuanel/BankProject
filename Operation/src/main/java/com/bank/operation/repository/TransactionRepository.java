package com.bank.operation.repository;

import com.bank.operation.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByState(String state);

    List<Transaction> findFirst10ByDateAndIbanPayerAndStateOrderByIdDesc(String date, String iban, String state);

    List<Transaction> findAllByDateIsGreaterThanEqualAndDateIsLessThanEqualAndIbanPayer
            (LocalDate startDate, LocalDate endDate, String ibanPayer);
}
