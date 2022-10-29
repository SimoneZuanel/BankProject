package com.bank.operation.controller;

import com.bank.operation.dto.BankTransferDto;
import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.WithdrawalDepositDto;
import com.bank.operation.serviceRabbit.AccountMessageSender;
import com.bank.operation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transaction")
@Validated
public class TransactionController {

    private TransactionService transactionService;
    private AccountMessageSender accountMessageSender;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountMessageSender accountMessageSender) {
        this.transactionService = transactionService;
        this.accountMessageSender = accountMessageSender;
    }


    @PostMapping (value = "/withdrawal")
    public void withdrawal(@RequestBody @Valid WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.withdrawal
                (withdrawalDepositDto.getIban(), withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(withdrawalDepositDto.getIban());
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageWithdrawal(transactionDto);
    }

    @PostMapping (value = "/deposit")
    public void deposit(@RequestBody @Valid WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.deposit
                (withdrawalDepositDto.getIban(), withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(withdrawalDepositDto.getIban());
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageDeposit(transactionDto);
    }

    @PostMapping (value = "/bankTransfer")
    public void bankTransfer(@RequestBody @Valid BankTransferDto bankTransferDto) {

        transactionService.bankTransfer(bankTransferDto.getIban(),
                bankTransferDto.getIbanBeneficiary(), bankTransferDto.getAmount(), bankTransferDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(bankTransferDto.getIban());
        transactionDto.setIbanBeneficiary(bankTransferDto.getIbanBeneficiary());
        transactionDto.setAmount(bankTransferDto.getAmount());

        accountMessageSender.sendAccountMessageBankTransfer(transactionDto);

    }

}
