package com.bank.operation.controller;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.WithdrawalDepositDto;
import com.bank.operation.service.AccountMessageSender;
import com.bank.operation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TransactionController {

    private TransactionService transactionService;
    private AccountMessageSender accountMessageSender;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountMessageSender accountMessageSender) {
        this.transactionService = transactionService;
        this.accountMessageSender = accountMessageSender;
    }


    @CrossOrigin("*")
    @PostMapping (value = "/withdrawal")
    public void withdrawal(@RequestBody WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.withdrawal
                (withdrawalDepositDto.getIbanPayer(), withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(withdrawalDepositDto.getIbanPayer());
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageWithdrawal(transactionDto);
    }

    @CrossOrigin("*")
    @PostMapping (value = "/deposit")
    public void deposit(@RequestBody WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.deposit
                (withdrawalDepositDto.getIbanPayer(), withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(withdrawalDepositDto.getIbanPayer());
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageDeposit(transactionDto);
    }

}
