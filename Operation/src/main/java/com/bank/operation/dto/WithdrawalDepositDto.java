package com.bank.operation.dto;

import lombok.Data;

@Data
public class WithdrawalDepositDto {

    private String ibanPayer;
    private Double amount;
    private String causal;
}
