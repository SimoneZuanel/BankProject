package com.bank.transaction.controller;

import com.bank.transaction.dto.IbanPayerDto;
import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.service.LoginAndRegistrationMessageSender;
import com.bank.transaction.service.PrintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class PrintController {

    private PrintsService printsService;
    private LoginAndRegistrationMessageSender loginAndRegistrationMessageSender;

    @Autowired
    public PrintController(PrintsService printsService, LoginAndRegistrationMessageSender loginAndRegistrationMessageSender) {
        this.printsService = printsService;
        this.loginAndRegistrationMessageSender = loginAndRegistrationMessageSender;
    }

    @CrossOrigin("*")
    @GetMapping(value = "/getAllUsers")
    public ArrayList getAllUsers(){
        return loginAndRegistrationMessageSender.sendUserListMessage(new ArrayList<>());
    }

    @CrossOrigin("*")
    @GetMapping(value = "/getLast10Transactions")
    public List<TransactionDto> getLast10Transactions(@RequestBody IbanPayerDto ibanPayerDto){
        return printsService.getLast10Transactions(ibanPayerDto.getIbanPayer());
    }
}
