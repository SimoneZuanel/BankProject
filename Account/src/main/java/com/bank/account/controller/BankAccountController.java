package com.bank.account.controller;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.dto.NumberAccountDto;
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


    @CrossOrigin("*")
    @GetMapping(value = "/getBankAccounts")
    public List<BankAccountDto> findAllBankAccounts(){
        return bankAccountService.findAllBankAccounts();
    }

    @CrossOrigin("*")
    @GetMapping(value = "/getBalance")
    public Double getBalance(@RequestParam String iban){
        return bankAccountService.getBalance(iban);
    }

    @CrossOrigin("*")
    @PostMapping(value = "/openingRequestBankAccount")
    public void openingRequestBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.openingRequestBankAccount(numberAccountDto.getNumberAccount());
    }

    @CrossOrigin("*")
    @GetMapping(value = "/closingRequestBankAccount")
    public void closingRequestBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.closingRequestBankAccount(numberAccountDto.getNumberAccount());
    }

    @CrossOrigin("*")
    @GetMapping(value = "/openBankAccount")
    public void openBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.openBankAccount(numberAccountDto.getNumberAccount());
    }

    @CrossOrigin("*")
    @DeleteMapping (value = "/closeBankAccount")
    public void closeBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.closeBankAccount(numberAccountDto.getNumberAccount());
    }

}
