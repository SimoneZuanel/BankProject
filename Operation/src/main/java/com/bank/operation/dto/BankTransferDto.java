package com.bank.operation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BankTransferDto extends WithdrawalDepositDto{

    @NotBlank(message = "Il campo non deve essere vuoto")
    private String ibanBeneficiary;

}
