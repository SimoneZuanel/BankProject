package com.bank.operation.dto;

import lombok.Data;

@Data
public class BankTransferDto extends WithdrawalDepositDto{

    private String ibanBeneficiary;

}
