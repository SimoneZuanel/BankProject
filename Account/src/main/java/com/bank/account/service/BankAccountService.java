package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.entity.BankAccount;
import com.bank.account.entity.BankAccountEnum;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import com.bank.apiBankException.AccountClosureFailedException;
import com.bank.apiBankException.AccountOpeningFailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UtilAccountService utilAccountService;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    public List<BankAccountDto> findAllBankAccounts(){

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();

        List<BankAccountDto> bankAccountDtoList = new ArrayList<>();

        for(BankAccount bankAccount : bankAccountList) {
            bankAccountDtoList.add(bankAccountMapper.toDto(bankAccount));
        }

        return bankAccountDtoList;
    }

    public Double getBalance(String iban){

        BankAccountDto bankAccountDto = bankAccountMapper.toDto(bankAccountRepository.findByIban(iban));

        return bankAccountDto.getBalance();
    }

    public void openingRequestBankAccount(String numberAccount, Double amount) throws AccountOpeningFailed {

        BankAccount oldBankAccount = bankAccountRepository.findByNumberAccount(numberAccount);

        BankAccountDto newBankAccountDto = new BankAccountDto();
        if (oldBankAccount == null) {
            throw new AccountOpeningFailed("account non trovato, non è possibile aprire un nuovo conto");

        } else {

            newBankAccountDto.setUsername(oldBankAccount.getUsername());
            newBankAccountDto.setBalance(amount);
            List<String> numberAccountsList = bankAccountRepository.findNumberAccounts();

            do {
                utilAccountService.generateIban();
            }
            while (numberAccountsList.contains(utilAccountService.getAccountNumber()));

            newBankAccountDto.setIban(utilAccountService.getIban());
            newBankAccountDto.setNumberAccount(utilAccountService.getAccountNumber());
            newBankAccountDto.setState(BankAccountEnum.OPENING_REQUEST);

            bankAccountRepository.save(bankAccountMapper.toEntity(newBankAccountDto));
        }

    }

    public void closingRequestBankAccount(String numberAccount) throws AccountClosureFailedException {
        BankAccountDto bankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(numberAccount));

        if(bankAccount.getState() == BankAccountEnum.ACTIVE){
            bankAccount.setState(BankAccountEnum.CLOSING_REQUEST);
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));

        }else if (bankAccount.getState() == BankAccountEnum.INACTIVE){
            throw new AccountClosureFailedException("l'account non è attivo");

        }else if (bankAccount.getState() == BankAccountEnum.OPENING_REQUEST){
            throw new AccountClosureFailedException("l'account è in fase di approvazione");

        }else{
            throw new AccountClosureFailedException("l'account non esiste");
        }

    }

    public void openFirstBankAccount(String numberAccount) throws AccountOpeningFailed {
        BankAccountDto bankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(numberAccount));

        if(bankAccount.getState() == BankAccountEnum.INACTIVE) {
            bankAccount.setState(BankAccountEnum.ACTIVE);
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));

        }else if (bankAccount.getState() == BankAccountEnum.ACTIVE){
            throw new AccountOpeningFailed("l'account è già attivo");

        }else if (bankAccount.getState() == BankAccountEnum.OPENING_REQUEST) {
            throw new AccountOpeningFailed("l'account è in fase di apertura");

        }else{
            throw new AccountOpeningFailed("l'account è in fase di chiusura");
        }

    }

    public void closeBankAccount(String numberAccount) throws AccountClosureFailedException {

        BankAccount bankAccount = bankAccountRepository.findByNumberAccount(numberAccount);
        BankAccountDto bankAccountDto;

        if (bankAccount == null)
            throw new AccountClosureFailedException("account non esistente, non è possibile chiudere il conto");
        else
            bankAccountDto = bankAccountMapper.toDto(bankAccount);
            bankAccountRepository.delete(bankAccountMapper.toEntity(bankAccountDto));
    }

    public void openAnotherBankAccount(String oldNumberAccount, String newNumberAccount) throws AccountOpeningFailed {

        BankAccountDto oldBankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(oldNumberAccount));
        BankAccountDto newBankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(newNumberAccount));

        if(newBankAccount.getState() == BankAccountEnum.OPENING_REQUEST && oldBankAccount.getBalance() > newBankAccount.getBalance()) {
            newBankAccount.setState(BankAccountEnum.ACTIVE);
            oldBankAccount.setBalance(oldBankAccount.getBalance() - newBankAccount.getBalance());
            bankAccountRepository.save(bankAccountMapper.toEntity(newBankAccount));
            bankAccountRepository.save(bankAccountMapper.toEntity(oldBankAccount));

        }else if (newBankAccount.getState() == BankAccountEnum.ACTIVE) {
            throw new AccountOpeningFailed("l'account è già attivo");

        } else if (newBankAccount.getState() == BankAccountEnum.CLOSING_REQUEST && newBankAccount.getState() == BankAccountEnum.INACTIVE) {
            throw new AccountOpeningFailed("il conto è inattivo o in fase di chiusura");

        }else{
            bankAccountRepository.delete(bankAccountMapper.toEntity(newBankAccount));
            throw new AccountOpeningFailed("il trasferimento di denaro non è andato a buon fine");
        }

    }

}

