package com.bank.transaction.dto;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.entity.TypeOfTransactionEnum;
import lombok.Data;

@Data
public class TypeOfTransactionDto {
    private Long id;
    private TypeOfTransactionEnum typeOfTransactionEnum;
    private Transaction transactionId;
}
