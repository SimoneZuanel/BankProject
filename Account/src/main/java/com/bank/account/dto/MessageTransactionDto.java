package com.bank.account.dto;

import lombok.Data;

@Data
public class MessageTransactionDto {
    private Long id;
    private String ibanPayer;
    private String ibanBeneficiary;
    private Double amount;
    private String date;
    private String causal;
    private String state;
}
