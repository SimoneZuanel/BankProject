package com.bank.operation.controller;

import com.bank.operation.dto.BankTransferDto;
import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.WithdrawalDepositDto;
import com.bank.operation.service.AccountMessageSender;
import com.bank.operation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private AccountMessageSender accountMessageSender;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountMessageSender accountMessageSender) {
        this.transactionService = transactionService;
        this.accountMessageSender = accountMessageSender;
    }


    @PostMapping (value = "/withdrawal")
    public void withdrawal(@RequestBody WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.withdrawal
                (withdrawalDepositDto.getIbanPayer(), withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(withdrawalDepositDto.getIbanPayer());
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageWithdrawal(transactionDto);
    }

    @PostMapping (value = "/deposit")
    public void deposit(@RequestBody WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.deposit
                (withdrawalDepositDto.getIbanPayer(), withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(withdrawalDepositDto.getIbanPayer());
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageDeposit(transactionDto);
    }

    @PostMapping (value = "/bankTransfer")
    public void bankTransfer(@RequestBody BankTransferDto bankTransferDto) {

        transactionService.bankTransfer(bankTransferDto.getIbanPayer(),
               bankTransferDto.getIbanBeneficiary(), bankTransferDto.getAmount(), bankTransferDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(bankTransferDto.getIbanPayer());
        transactionDto.setIbanBeneficiary(bankTransferDto.getIbanBeneficiary());
        transactionDto.setAmount(bankTransferDto.getAmount());

        accountMessageSender.sendAccountMessageBankTransfer(transactionDto);

    }

}
