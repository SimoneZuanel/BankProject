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

    @GetMapping(value = "/getBalance")
    public Double getBalance(@RequestParam String iban){
        return bankAccountService.getBalance(iban);
    }

    @PostMapping(value = "/openingRequestBankAccount")
    public void openingRequestBankAccount(@RequestParam String numberAccount){
        bankAccountService.openingRequestBankAccount(numberAccount);
    }

    @GetMapping(value = "/closingRequestBankAccount")
    public void closingRequestBankAccount(@RequestParam String numberAccount){
        bankAccountService.closingRequestBankAccount(numberAccount);
    }

    @GetMapping(value = "/openBankAccount")
    public void openBankAccount(@RequestParam String numberAccount){
        bankAccountService.openBankAccount(numberAccount);
    }

    @DeleteMapping (value = "/closeBankAccount")
    public void closeBankAccount(@RequestParam String numberAccount){
        bankAccountService.closeBankAccount(numberAccount);
    }

}
