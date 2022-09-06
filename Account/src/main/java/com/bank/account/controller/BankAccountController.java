package com.bank.account.controller;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class BankAccountController {

    private BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping(value = "/getBankAccounts")
    public List<BankAccountDto> findAllBankAccounts(){
        return bankAccountService.findAllBankAccounts();
    }

    @GetMapping(value = "/activeBankAccount")
    public void activeBankAccount(@RequestParam String numberAccount){
        bankAccountService.activeBankAccount(numberAccount);
    }



}
