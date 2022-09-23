package com.bank.operation.repository;

import com.bank.operation.entity.Transaction;
import com.bank.operation.entity.TypeOfTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfTransactionRepository extends JpaRepository<TypeOfTransaction, Integer> {

    TypeOfTransaction findByTransactionId(Transaction transactionId);
}
