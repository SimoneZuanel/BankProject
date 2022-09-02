package com.bank.account.service;

import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class BankAccountService {



    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountMapper bankAccountMapper;


}
