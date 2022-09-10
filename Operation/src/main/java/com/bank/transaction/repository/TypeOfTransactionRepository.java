package com.bank.transaction.repository;

import com.bank.transaction.entity.TypeOfTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfTransactionRepository extends JpaRepository<TypeOfTransaction, Integer> {

}
