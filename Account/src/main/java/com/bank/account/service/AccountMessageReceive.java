package com.bank.account.service;

import com.bank.account.entity.BankAccount;
import com.bank.account.repository.BankAccountRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageReceive {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @RabbitListener(queues = "newAccount")
    public Boolean receive(String username) {

        try {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setUsername(username);

            bankAccountRepository.save(bankAccount);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
