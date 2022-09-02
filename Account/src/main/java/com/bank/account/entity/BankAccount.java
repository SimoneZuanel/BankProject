package com.bank.account.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bank_account")
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
    private String number_account;

    @Column(name = "state")
    private String state;

}
