package com.bank.operation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WithdrawalDepositDto {

    @NotBlank(message = "Il campo non deve essere vuoto")
    private String iban;

    @NotBlank(message = "Il campo non deve essere vuoto")
    private Double amount;

    @NotBlank(message = "Il campo non deve essere vuoto")
    private String causal;
}
