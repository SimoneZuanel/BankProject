package com.bank.operation.service;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.enumeration.TypeOfTransactionEnum;
import com.bank.operation.mapper.TransactionMapper;
import com.bank.operation.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    public void withdrawal(String ibanPayer, Double amount, String causal){

        TransactionDto withdrawalDto = new TransactionDto();
        withdrawalDto.setIbanPayer(ibanPayer);
        withdrawalDto.setIbanBeneficiary("not required");
        withdrawalDto.setAmount(-amount);
        withdrawalDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        withdrawalDto.setCausal(causal);
        withdrawalDto.setState("loading");
        withdrawalDto.setTypeOfTransactionEnum(TypeOfTransactionEnum.WITHDRAWAL);

        transactionRepository.save(transactionMapper.toEntity(withdrawalDto));

    }

    public void deposit(String ibanPayer, Double amount, String causal){

        TransactionDto depositDto = new TransactionDto();
        depositDto.setIbanPayer(ibanPayer);
        depositDto.setIbanBeneficiary("not required");
        depositDto.setAmount(amount);
        depositDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        depositDto.setCausal(causal);
        depositDto.setState("loading");
        depositDto.setTypeOfTransactionEnum(TypeOfTransactionEnum.DEPOSIT);

        transactionRepository.save(transactionMapper.toEntity(depositDto));

    }

    public void bankTransfer(String ibanPayer, String ibanBeneficiary, Double amount, String causal) {

        TransactionDto bankTransferPayerDto = new TransactionDto();
        bankTransferPayerDto.setIbanPayer(ibanPayer);
        bankTransferPayerDto.setIbanBeneficiary(ibanBeneficiary);
        bankTransferPayerDto.setAmount(-amount);
        bankTransferPayerDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        bankTransferPayerDto.setCausal(causal);
        bankTransferPayerDto.setState("loading");
        bankTransferPayerDto.setTypeOfTransactionEnum(TypeOfTransactionEnum.BANK_TRANSFER);


        TransactionDto bankTransferBeneficiaryDto = new TransactionDto();
        bankTransferBeneficiaryDto.setIbanPayer(ibanBeneficiary);
        bankTransferBeneficiaryDto.setIbanBeneficiary(ibanPayer);
        bankTransferBeneficiaryDto.setAmount(amount);
        bankTransferBeneficiaryDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        bankTransferBeneficiaryDto.setCausal(causal);
        bankTransferBeneficiaryDto.setState("loading");
        bankTransferBeneficiaryDto.setTypeOfTransactionEnum(TypeOfTransactionEnum.BANK_TRANSFER);

        transactionRepository.save(transactionMapper.toEntity(bankTransferPayerDto));
        transactionRepository.save(transactionMapper.toEntity(bankTransferBeneficiaryDto));

    }

}
