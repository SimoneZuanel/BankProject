package com.bank.operation.dto;

import com.bank.operation.entity.Transaction;
import com.bank.operation.entity.TypeOfTransactionEnum;
import lombok.Data;

@Data
public class TypeOfTransactionDto {
    private Long id;
    private TypeOfTransactionEnum typeOfTransactionEnum;
    private Transaction transactionId;
}
