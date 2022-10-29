package com.bank.operation.repository;

import com.bank.operation.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByState(String state);

    List<Transaction> findAllByState(String state);

    List<Transaction> findFirst10ByDateIsLessThanEqualAndIbanPayerAndStateOrderByIdDesc(String date, String iban, String state);

    List<Transaction> findAllByDateIsGreaterThanEqualAndDateIsLessThanEqualAndIbanPayer
            (String startDate, String endDate, String ibanPayer);
}
