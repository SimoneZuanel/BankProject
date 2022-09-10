package com.bank.operation.service;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.entity.Transaction;
import com.bank.operation.mapper.TransactionMapper;
import com.bank.operation.repository.TransactionRepository;
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
    private TransactionMapper transactionMapper;




    public List<TransactionDto> getLast10Transactions(String iban){
        List<Transaction> transactionList =
                transactionRepository.findFirst10ByDateAndIbanPayerAndStateOrderByIdDesc
                        (LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), iban, "success");

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for(Transaction transaction : transactionList) {
            transactionDtoList.add(transactionMapper.toDto(transaction));
        }

        return transactionDtoList;
    }
}
