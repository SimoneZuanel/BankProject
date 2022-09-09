package com.bank.transaction.dto;

import lombok.Data;

@Data
public class TransactionDto {

    private Long id;
    private String ibanPayer;
    private String ibanBeneficiary;
    private Double amount;
    private String date;
    private String causal;
    private String state;
}
