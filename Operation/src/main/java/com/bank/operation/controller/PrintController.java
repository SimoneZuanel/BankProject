package com.bank.operation.controller;

import com.bank.operation.dto.GetAllTransactionDto;
import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.TypeOfTransactionDto;
import com.bank.operation.service.AccountMessageSender;
import com.bank.operation.service.LoginAndRegistrationMessageSender;
import com.bank.operation.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/print")
public class PrintController {

    private PrintService printService;
    private LoginAndRegistrationMessageSender loginAndRegistrationMessageSender;

    private AccountMessageSender accountMessageSender;

    @Autowired
    public PrintController(PrintService printService, LoginAndRegistrationMessageSender loginAndRegistrationMessageSender,
                           AccountMessageSender accountMessageSender) {
        this.printService = printService;
        this.loginAndRegistrationMessageSender = loginAndRegistrationMessageSender;
        this.accountMessageSender = accountMessageSender;
    }


    @GetMapping(value = "/getAllUsers")
    public ArrayList getAllUsers(){
        return loginAndRegistrationMessageSender.sendUserListMessage(new ArrayList<>());
    }

    @GetMapping(value = "/getLast10Transactions")
    public List<TypeOfTransactionDto> getLast10Transactions(@RequestParam String iban){
        return printService.getLast10Transactions(iban);
    }

    @GetMapping(value = "/getAllTransactions")
    public List<TransactionDto> getAllTransactions(@RequestBody GetAllTransactionDto getAllTransactionDto){

        ArrayList<String> ibanList = accountMessageSender.sendIbanListMessage(getAllTransactionDto.getUsername());

        return printService.getAllTransactions
                (ibanList, getAllTransactionDto.getStartDate(), getAllTransactionDto.getEndDate());

    }
}
