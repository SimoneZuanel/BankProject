package com.bank.account.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NumberAccountDto {

    @NotBlank(message = "Tutti i campi devono essere riempiti")
    private String numberAccount;
}
