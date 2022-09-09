package com.bank.transaction.controller;

import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.service.AccountMessageSender;
import com.bank.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/findLast10Transactions")
    public List<TransactionDto> findLast10Transactions(@RequestParam String ibanPayer){
        return transactionService.findLast10Transactions(ibanPayer);
    }

    @PostMapping (value = "/withdrawal")
    public void withdrawal(@RequestParam String ibanPayer,
                           @RequestParam Double amount,
                           @RequestParam String causal) {

        transactionService.withdrawal(ibanPayer, amount, causal);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(ibanPayer);
        transactionDto.setAmount(amount);

        accountMessageSender.sendAccountMessageWithdrawal(transactionDto);
    }

    @PostMapping (value = "/deposit")
    public void deposit(@RequestParam("iban_payer") String ibanPayer,
                           @RequestParam("amount") Double amount,
                           @RequestParam("causal") String causal) {

        transactionService.deposit(ibanPayer, amount, causal);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(ibanPayer);
        transactionDto.setAmount(amount);

        accountMessageSender.sendAccountMessageDeposit(transactionDto);
    }

}
