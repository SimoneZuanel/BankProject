package com.bank.account.controller;

import com.bank.account.dto.*;

import com.bank.account.service.BankAccountService;
import com.bank.account.serviceRabbit.LoginAndRegistrationMessageSender;
import com.bank.apiBankException.AccountClosureFailedException;
import com.bank.apiBankException.AccountOpeningFailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/account")
@Validated
public class BankAccountController {

    private BankAccountService bankAccountService;
    private LoginAndRegistrationMessageSender loginAndRegistrationMessageSender;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, LoginAndRegistrationMessageSender loginAndRegistrationMessageSender) {
        this.bankAccountService = bankAccountService;
        this.loginAndRegistrationMessageSender = loginAndRegistrationMessageSender;
    }


    @GetMapping(value = "/getBankAccounts")
    public List<BankAccountDto> findAllBankAccounts(){
        return bankAccountService.findAllBankAccounts();
    }


    @GetMapping(value = "/{username}")
    public UserBankAccountDto getUserByUsername(@PathVariable String username){
        return loginAndRegistrationMessageSender.sendUserMessage(username);
    }

    @PostMapping(value = "/openingRequestBankAccount")
    public void openingRequestBankAccount(@RequestBody @Valid NewBankAccountDto newBankAccountDto) throws AccountOpeningFailed {
        bankAccountService.openingRequestBankAccount(newBankAccountDto.getNumberAccount(), newBankAccountDto.getAmount());
    }

    @PutMapping(value = "/closingRequestBankAccount")
    public void closingRequestBankAccount(@RequestBody @Valid NumberAccountDto numberAccountDto) throws AccountClosureFailedException {
        bankAccountService.closingRequestBankAccount(numberAccountDto.getNumberAccount());
    }

    @PutMapping(value = "/openFirstBankAccount")
    public void openFirstBankAccount(@RequestBody @Valid NumberAccountDto numberAccountDto) throws AccountOpeningFailed {
        bankAccountService.openFirstBankAccount(numberAccountDto.getNumberAccount());
    }

    @PutMapping(value = "/openAnotherBankAccount")
    public void openAnotherBankAccount(@RequestBody @Valid NumberAccountDto numberAccountDto) throws AccountOpeningFailed {
        bankAccountService.openAnotherBankAccount
                (numberAccountDto.getNumberAccount());
    }

    @DeleteMapping (value = "/closeBankAccount/{numberAccount}")
    public void closeBankAccount(@PathVariable String numberAccount) throws AccountClosureFailedException {
        bankAccountService.closeBankAccount(numberAccount);
    }

}
