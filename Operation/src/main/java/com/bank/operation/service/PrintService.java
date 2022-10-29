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
                transactionRepository.findFirst10ByDateIsLessThanEqualAndIbanPayerAndStateOrderByIdDesc
                        (LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), iban, "success");

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for(Transaction transaction : transactionList) {

            TransactionDto transactionDto = transactionMapper.toDto(transaction);

            transactionDtoList.add(transactionDto);

        }

        return transactionDtoList;
    }

    public List<TransactionDto> getAllTransactions(ArrayList<String> ibanList, String startDate, String endDate){

        String[] splitStartDate = startDate.split("-");
        String strDate = splitStartDate[2] + "/" + splitStartDate[1] + "/" + splitStartDate[0];
        String[] endSplitDate = endDate.split("-");
        String enDate = endSplitDate[2] + "/" + endSplitDate[1] + "/" + endSplitDate[0];

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for(String iban : ibanList){
           List<Transaction> transactionList =
                   transactionRepository.findAllByDateIsGreaterThanEqualAndDateIsLessThanEqualAndIbanPayer(strDate,enDate,iban);

            for(Transaction transaction : transactionList) {
                TransactionDto transactionDto = transactionMapper.toDto(transaction);

                transactionDtoList.add(transactionDto);
            }
        }

        return transactionDtoList;
    }
}
