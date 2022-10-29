package com.bank.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewBankAccountDto extends NumberAccountDto {

    private Double amount;
}
