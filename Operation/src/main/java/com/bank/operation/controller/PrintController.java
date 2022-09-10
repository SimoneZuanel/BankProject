package com.bank.operation.controller;

import com.bank.operation.dto.IbanPayerDto;
import com.bank.operation.dto.TransactionDto;
import com.bank.operation.service.LoginAndRegistrationMessageSender;
import com.bank.operation.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class PrintController {

    private PrintService printService;
    private LoginAndRegistrationMessageSender loginAndRegistrationMessageSender;

    @Autowired
    public PrintController(PrintService printService, LoginAndRegistrationMessageSender loginAndRegistrationMessageSender) {
        this.printService = printService;
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
        return printService.getLast10Transactions(ibanPayerDto.getIbanPayer());
    }
}
