package com.bank.operation.controller;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.dto.TypeOfTransactionDto;
import com.bank.operation.service.AccountMessageSender;
import com.bank.operation.service.LoginAndRegistrationMessageSender;
import com.bank.operation.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/print")
@Validated
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

    @GetMapping(value = "/getLast10Transactions/{iban}")
    public List<TypeOfTransactionDto> getLast10Transactions(@PathVariable String iban){
        return printService.getLast10Transactions(iban);
    }

    @GetMapping(value = "/getAllTransactions/{username}/{startDate}/{endDate}")
    public List<TransactionDto> getAllTransactions(@PathVariable String username,
                                                   @PathVariable LocalDate startDate,
                                                   @PathVariable LocalDate endDate){

        ArrayList<String> ibanList = accountMessageSender.sendIbanListMessage(username);

        return printService.getAllTransactions(ibanList, startDate, endDate);

    }
}
