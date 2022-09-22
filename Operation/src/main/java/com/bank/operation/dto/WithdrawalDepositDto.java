package com.bank.operation.dto;

import lombok.Data;

@Data
public class WithdrawalDepositDto {

    private String iban;
    private Double amount;
    private String causal;
}
