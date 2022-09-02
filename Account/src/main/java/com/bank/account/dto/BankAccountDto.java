package com.bank.account.dto;

import lombok.Data;

@Data
public class BankAccountDto {

    private Long id;
    private String username;
    private String iban;
    private Double balance;
    private String number_account;
    private String state;

}
