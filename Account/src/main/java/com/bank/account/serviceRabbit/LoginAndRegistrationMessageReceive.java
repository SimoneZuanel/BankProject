package com.bank.account.serviceRabbit;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.enumeration.BankAccountEnum;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import com.bank.account.service.UtilAccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginAndRegistrationMessageReceive {

    @Autowired
    private UtilAccountService utilAccountService;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BankAccountRepository bankAccountRepository;


    @RabbitListener(queues = "newAccount")
    public Boolean receiveUserMessage(String username) {
        try {
            BankAccountDto bankAccountDto = new BankAccountDto();
            bankAccountDto.setUsername(username);
            bankAccountDto.setBalance(0.0);
            List<String> numberAccountsList = bankAccountRepository.findNumberAccounts();

            do {
                utilAccountService.generateIban();
            }
            while (numberAccountsList.contains(utilAccountService.getAccountNumber()));

            bankAccountDto.setIban(utilAccountService.getIban());
            bankAccountDto.setNumberAccount(utilAccountService.getAccountNumber());
            bankAccountDto.setState(BankAccountEnum.INACTIVE);

            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));

            return true;

        } catch (Exception e) {
            System.out.println("Error during BankAccount registration");
            return false;
        }

    }

}