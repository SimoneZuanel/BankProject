package com.bank.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewBankAccountDto extends NumberAccountDto {

    @NotBlank(message = "Nessun campo può essere lasciato vuoto")
    private Double amount;
}
