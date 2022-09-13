package com.bank.account.controller;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.dto.NewAnotherBankAccountDto;
import com.bank.account.dto.NewBankAccountDto;
import com.bank.account.dto.NumberAccountDto;
import com.bank.account.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
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
    @GetMapping(value = "/{iban}/getBalance")
    public Double getBalance(@PathVariable String iban){
        return bankAccountService.getBalance(iban);
    }

    @CrossOrigin("*")
    @PostMapping(value = "/openingRequestBankAccount")
    public void openingRequestBankAccount(@RequestBody NewBankAccountDto newBankAccountDto){
        bankAccountService.openingRequestBankAccount(newBankAccountDto.getNumberAccount(), newBankAccountDto.getAmount());
    }

    @CrossOrigin("*")
    @PutMapping(value = "/closingRequestBankAccount")
    public void closingRequestBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.closingRequestBankAccount(numberAccountDto.getNumberAccount());
    }

    @CrossOrigin("*")
    @PutMapping(value = "/openFirstBankAccount")
    public void openFirstBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.openFirstBankAccount(numberAccountDto.getNumberAccount());
    }

    @CrossOrigin("*")
    @PutMapping(value = "/openAnotherBankAccount")
    public void openAnotherBankAccount(@RequestBody NewAnotherBankAccountDto newAnotherBankAccountDto){
        bankAccountService.openAnotherBankAccount
                (newAnotherBankAccountDto.getOldBankAccount(), newAnotherBankAccountDto.getNewBankAccount());
    }

    @CrossOrigin("*")
    @DeleteMapping (value = "/closeBankAccount")
    public void closeBankAccount(@RequestBody NumberAccountDto numberAccountDto){
        bankAccountService.closeBankAccount(numberAccountDto.getNumberAccount());
    }

}
