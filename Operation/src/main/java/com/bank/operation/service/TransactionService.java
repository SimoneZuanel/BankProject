package com.bank.operation.service;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.TypeOfTransactionDto;
import com.bank.operation.entity.TypeOfTransactionEnum;
import com.bank.operation.mapper.TransactionMapper;
import com.bank.operation.mapper.TypeOfTransactionMapper;
import com.bank.operation.repository.TypeOfTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    @Autowired
    private TypeOfTransactionRepository typeOfTransactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TypeOfTransactionMapper typeOfTransactionMapper;



    public void withdrawal(String ibanPayer, Double amount, String causal){

        TransactionDto withdrawalDto = new TransactionDto();
        withdrawalDto.setIbanPayer(ibanPayer);
        withdrawalDto.setIbanBeneficiary("not required");
        withdrawalDto.setAmount(amount);
        withdrawalDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        withdrawalDto.setCausal(causal);
        withdrawalDto.setState("loading");

        TypeOfTransactionDto typeOfTransactionDto = new TypeOfTransactionDto();
        typeOfTransactionDto.setTypeOfTransactionEnum(TypeOfTransactionEnum.WITHDRAWAL);
        typeOfTransactionDto.setTransactionId(transactionMapper.toEntity(withdrawalDto));

        typeOfTransactionRepository.save(typeOfTransactionMapper.toEntity(typeOfTransactionDto));

    }


    public void deposit(String ibanPayer, Double amount, String causal){

        TransactionDto depositDto = new TransactionDto();
        depositDto.setIbanPayer(ibanPayer);
        depositDto.setIbanBeneficiary("not required");
        depositDto.setAmount(amount);
        depositDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        depositDto.setCausal(causal);
        depositDto.setState("loading");

        TypeOfTransactionDto typeOfTransactionDto = new TypeOfTransactionDto();
        typeOfTransactionDto.setTypeOfTransactionEnum(TypeOfTransactionEnum.DEPOSIT);
        typeOfTransactionDto.setTransactionId(transactionMapper.toEntity(depositDto));

        typeOfTransactionRepository.save(typeOfTransactionMapper.toEntity(typeOfTransactionDto));

    }

    /*public void BankTransfer(String ibanPayer, String ibanBeneficiary, Double amount, String causal, String Data) {

        withdrawal(ibanPayer, amount, "Transfer to " + ibanBeneficiary);
        withdrawalDto.setIbanPayer(ibanPayer);
        withdrawalDto.setIbanBeneficiary(ibanBeneficiary);
        withdrawalDto.setAmount(amount);
        withdrawalDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        withdrawalDto.setCausal("Transfer to " + ibanBeneficiary);*/


        /*deposit(ibanBeneficiary, amount, "Transfer from "+ ibanPayer);
        /*depositDto.setIbanPayer(ibanPayer);
        depositDto.setIbanBeneficiary(ibanBeneficiary);
        depositDto.setAmount(amount);
        depositDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        depositDto.setCausal("Transfer from "+ ibanPayer);
    }*/

}
