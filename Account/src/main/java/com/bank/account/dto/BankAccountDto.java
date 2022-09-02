package com.bank.account.dto;

import com.bank.account.entity.BankAccountEnum;
import lombok.Data;

@Data
public class BankAccountDto {

    private Long id;
    private String username;
    private String iban;
    private Double balance;
    private String numberAccount;
    private BankAccountEnum state;

}
