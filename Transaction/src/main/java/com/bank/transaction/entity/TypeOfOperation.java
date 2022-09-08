package com.bank.transaction.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "type_of_operations")
@Data
public class TypeOfOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type_of_operation")
    private TypeOfOperationEnum typeOfOperation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transactions_id")
    private Transaction transactionId;
}
