package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.entity.BankAccountEnum;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageReceive {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @RabbitListener(queues = "newAccount")
    public Boolean receive(String username) {
        try {
            BankAccountDto bankAccountDto = new BankAccountDto();
            bankAccountDto.setUsername(username);
            bankAccountDto.setIban(userAccountService.generateIban());
            bankAccountDto.setBalance(0.23);
            bankAccountDto.setNumberAccount(userAccountService.getAccountNumber());
            bankAccountDto.setState(BankAccountEnum.INACTIVE);
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
