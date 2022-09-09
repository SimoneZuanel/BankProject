package com.bank.transaction.controller;

import com.bank.transaction.dto.IbanPayerDto;
import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.dto.WithdrawalDepositDto;
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

    @CrossOrigin("*")
    @GetMapping(value = "/findLast10Transactions")
    public List<TransactionDto> findLast10Transactions(@RequestBody IbanPayerDto ibanPayerDto){
        return transactionService.findLast10Transactions(ibanPayerDto.getIbanPayer());
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
