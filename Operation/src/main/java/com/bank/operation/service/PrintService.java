package com.bank.operation.service;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.TypeOfTransactionDto;
import com.bank.operation.entity.Transaction;
import com.bank.operation.mapper.TransactionMapper;
import com.bank.operation.mapper.TypeOfTransactionMapper;
import com.bank.operation.repository.TransactionRepository;
import com.bank.operation.repository.TypeOfTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrintService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TypeOfTransactionRepository typeOfTransactionRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TypeOfTransactionMapper typeOfTransactionMapper;

    public List<TypeOfTransactionDto> getLast10Transactions(String iban){
        List<Transaction> transactionList =
                transactionRepository.findFirst10ByDateAndIbanPayerAndStateOrderByIdDesc
                        (LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), iban, "success");

        List<TypeOfTransactionDto> transactionDtoList = new ArrayList<>();

        for(Transaction transaction : transactionList) {

            TypeOfTransactionDto typeOfTransactionDto =
                    typeOfTransactionMapper.toDto(typeOfTransactionRepository.findByTransactionId(transaction));

            transactionDtoList.add(typeOfTransactionDto);

        }

        return transactionDtoList;
    }

    public List<TransactionDto> getAllTransactions(ArrayList<String> ibanList, LocalDate startDate, LocalDate endDate){

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for(String iban : ibanList){
           List<Transaction> transactionList =
                   transactionRepository.findAllByDateIsGreaterThanEqualAndDateIsLessThanEqualAndIbanPayer(startDate, endDate, iban);

            for(Transaction transaction : transactionList) {
                transactionDtoList.add(transactionMapper.toDto(transaction));
            }
        }

        return transactionDtoList;
    }
}
