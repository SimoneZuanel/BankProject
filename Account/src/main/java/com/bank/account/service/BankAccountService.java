package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.entity.BankAccount;
import com.bank.account.entity.BankAccountEnum;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
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

    public void openingRequestBankAccount(String numberAccount) {

        BankAccount oldBankAccount = bankAccountRepository.findByNumberAccount(numberAccount);

        BankAccountDto newBankAccountDto = new BankAccountDto();

        if (oldBankAccount == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "account non trovato, non è possibile aprire un nuovo conto");
        else

        newBankAccountDto.setUsername(oldBankAccount.getUsername());
        newBankAccountDto.setBalance(0.0);
        List<String> numberAccountsList = bankAccountRepository.findNumberAccounts();

        while (numberAccountsList.contains(utilAccountService.getAccountNumber())){
            utilAccountService.generateIban();
        }

        newBankAccountDto.setIban(utilAccountService.getIban());
        newBankAccountDto.setNumberAccount(utilAccountService.getAccountNumber());
        newBankAccountDto.setState(BankAccountEnum.OPENING_REQUEST);

        bankAccountRepository.save(bankAccountMapper.toEntity(newBankAccountDto));
    }

    public void closingRequestBankAccount(String numberAccount) {
        BankAccountDto bankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(numberAccount));

        if(bankAccount.getState() == BankAccountEnum.ACTIVE){
            bankAccount.setState(BankAccountEnum.CLOSING_REQUEST);
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));

        }else if (bankAccount.getState() == BankAccountEnum.INACTIVE){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account non è attivo");

        }else if (bankAccount.getState() == BankAccountEnum.OPENING_REQUEST){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account è in fase di approvazione");

        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account non esiste");
        }

    }

    public void openBankAccount(String numberAccount) {
        BankAccountDto bankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(numberAccount));

        if(bankAccount.getState() == BankAccountEnum.INACTIVE || bankAccount.getState() == BankAccountEnum.OPENING_REQUEST){
            bankAccount.setState(BankAccountEnum.ACTIVE);
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));

        }else if (bankAccount.getState() == BankAccountEnum.ACTIVE){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account è già attivo");

        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account è in fase di chiusura");
        }

    }

    public void closeBankAccount(String numberAccount) {

        BankAccount bankAccount = bankAccountRepository.findByNumberAccount(numberAccount);
        BankAccountDto bankAccountDto;

        if (bankAccount == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "account non esistente, non è possibile chiudere il conto");
        else
            bankAccountDto = bankAccountMapper.toDto(bankAccount);
            bankAccountRepository.delete(bankAccountMapper.toEntity(bankAccountDto));
    }

}

