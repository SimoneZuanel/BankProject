package com.bank.account.entity;

import com.bank.account.enumeration.BankAccountEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bank_accounts")
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "iban")
    private String iban;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "number_account")
    private String numberAccount;

    @Column(name = "state")
    private BankAccountEnum state;

}
