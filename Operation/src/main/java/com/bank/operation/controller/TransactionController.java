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


    @PostMapping (value = "/{iban}/withdrawal")
    public void withdrawal(@PathVariable String iban,
                           @RequestBody WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.withdrawal
                (iban, withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(iban);
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageWithdrawal(transactionDto);
    }

    @PostMapping (value = "/{iban}/deposit")
    public void deposit( @PathVariable String iban,
                         @RequestBody WithdrawalDepositDto withdrawalDepositDto) {

        transactionService.deposit
                (iban, withdrawalDepositDto.getAmount(), withdrawalDepositDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(iban);
        transactionDto.setAmount(withdrawalDepositDto.getAmount());

        accountMessageSender.sendAccountMessageDeposit(transactionDto);
    }

    @PostMapping (value = "/{iban}/bankTransfer")
    public void bankTransfer(@PathVariable String iban,
                             @RequestBody BankTransferDto bankTransferDto) {

        transactionService.bankTransfer(iban,
                bankTransferDto.getIbanBeneficiary(), bankTransferDto.getAmount(), bankTransferDto.getCausal());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIbanPayer(iban);
        transactionDto.setIbanBeneficiary(bankTransferDto.getIbanBeneficiary());
        transactionDto.setAmount(bankTransferDto.getAmount());

        accountMessageSender.sendAccountMessageBankTransfer(transactionDto);

    }

}
