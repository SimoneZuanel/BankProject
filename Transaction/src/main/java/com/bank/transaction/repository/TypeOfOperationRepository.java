package com.bank.transaction.repository;

import com.bank.transaction.entity.TypeOfOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfOperationRepository extends JpaRepository<TypeOfOperation, Integer> {

}
