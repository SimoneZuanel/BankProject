package com.bank.transaction.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "iban_payer")
    private String ibanPayer;

    @Column(name = "iban_beneficiary")
    private String ibanBeneficiary;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private String date;

    @Column(name = "causal")
    private String causal;

    @Column(name = "state")
    private String state;

}
