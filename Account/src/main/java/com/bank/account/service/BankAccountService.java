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
    private BankAccountMapper bankAccountMapper;


    public List<BankAccountDto> findAllBankAccounts(){

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();

        List<BankAccountDto> bankAccountDtoList = new ArrayList<>();

        for(BankAccount bankAccount : bankAccountList) {
            bankAccountDtoList.add(bankAccountMapper.toDto(bankAccount));
        }

        return bankAccountDtoList;
    }

    public void activeBankAccount(String numberAccount) {
        BankAccountDto bankAccount = bankAccountMapper.toDto(bankAccountRepository.findByNumberAccount(numberAccount));

        if(bankAccount.getState() == BankAccountEnum.INACTIVE){
            bankAccount.setState(BankAccountEnum.ACTIVE);
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccount));

        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "account già attivo");
        }

    }


}
