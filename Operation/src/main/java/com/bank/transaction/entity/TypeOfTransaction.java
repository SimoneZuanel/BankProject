package com.bank.transaction.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "type_of_transactions")
@Data
public class TypeOfTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type_of_transactions")
    private TypeOfTransactionEnum typeOfTransactionEnum;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transactions_id")
    private Transaction transactionId;
}
