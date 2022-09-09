package com.bank.transaction.service;

import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.dto.TypeOfOperationDto;
import com.bank.transaction.entity.Transaction;
import com.bank.transaction.entity.TypeOfOperationEnum;
import com.bank.transaction.mapper.TransactionMapper;
import com.bank.transaction.mapper.TypeOfOperationMapper;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.repository.TypeOfOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TypeOfOperationRepository typeOfOperationRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TypeOfOperationMapper typeOfOperationMapper;


    public List<TransactionDto> findAllTransactions(){

        List<Transaction> transactionList = transactionRepository.findAll();

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for(Transaction transaction : transactionList) {
            transactionDtoList.add(transactionMapper.toDto(transaction));
        }

        return transactionDtoList;
    }

    public List<TransactionDto> findLast10Transactions(String iban){
        List<Transaction> transactionList =
                transactionRepository.findFirst10ByDateAndIbanPayerAndStateOrderByIdDesc
                        (LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), iban, "success");

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for(Transaction transaction : transactionList) {
            transactionDtoList.add(transactionMapper.toDto(transaction));
        }

        return transactionDtoList;
    }

    public void withdrawal(String ibanPayer, Double amount, String causal){

        TransactionDto withdrawalDto = new TransactionDto();
        withdrawalDto.setIbanPayer(ibanPayer);
        withdrawalDto.setIbanBeneficiary("not required");
        withdrawalDto.setAmount(amount);
        withdrawalDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        withdrawalDto.setCausal(causal);
        withdrawalDto.setState("loading");

        TypeOfOperationDto typeOfOperationDto = new TypeOfOperationDto();
        typeOfOperationDto.setTypeOfOperation(TypeOfOperationEnum.WITHDRAWAL);
        typeOfOperationDto.setTransactionId(transactionMapper.toEntity(withdrawalDto));

        typeOfOperationRepository.save(typeOfOperationMapper.toEntity(typeOfOperationDto));

    }


    public void deposit(String ibanPayer, Double amount, String causal){

        TransactionDto depositDto = new TransactionDto();
        depositDto.setIbanPayer(ibanPayer);
        depositDto.setIbanBeneficiary("not required");
        depositDto.setAmount(amount);
        depositDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        depositDto.setCausal(causal);
        depositDto.setState("loading");

        TypeOfOperationDto typeOfOperationDto = new TypeOfOperationDto();
        typeOfOperationDto.setTypeOfOperation(TypeOfOperationEnum.DEPOSIT);
        typeOfOperationDto.setTransactionId(transactionMapper.toEntity(depositDto));

        typeOfOperationRepository.save(typeOfOperationMapper.toEntity(typeOfOperationDto));

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
