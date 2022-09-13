package com.bank.account.dto;

import lombok.Data;

@Data
public class NewBankAccountDto extends NumberAccountDto {

    private Double amount;
}
