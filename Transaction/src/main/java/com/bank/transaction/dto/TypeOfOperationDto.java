package com.bank.transaction.dto;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.entity.TypeOfOperationEnum;
import lombok.Data;

@Data
public class TypeOfOperationDto {
    private Long id;
    private TypeOfOperationEnum typeOfOperation;
    private Transaction transactionId;
}
