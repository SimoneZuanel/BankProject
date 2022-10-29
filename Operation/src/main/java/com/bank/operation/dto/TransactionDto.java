package com.bank.operation.dto;

import com.bank.operation.enumeration.TypeOfTransactionEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TransactionDto {

    @NotBlank(message = "Ogni campo deve essere inserito")
    private Long id;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private String ibanPayer;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private String ibanBeneficiary;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private Double amount;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private String date;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private String causal;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private String state;

    @NotBlank(message = "Ogni campo deve essere inserito")
    private TypeOfTransactionEnum typeOfTransactionEnum;

}
