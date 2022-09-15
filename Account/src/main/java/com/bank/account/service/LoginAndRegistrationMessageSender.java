package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.dto.UserBankAccountDto;
import com.bank.account.entity.BankAccount;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import com.bank.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginAndRegistrationMessageSender {

    private final RabbitTemplate rabbitTemplate;

    private BankAccountMapper bankAccountMapper;

    private BankAccountRepository bankAccountRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoginAndRegistrationMessageSender.class);

    public LoginAndRegistrationMessageSender
            (RabbitTemplate rabbitTemplate, BankAccountMapper bankAccountMapper, BankAccountRepository bankAccountRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.bankAccountMapper = bankAccountMapper;
        this.bankAccountRepository = bankAccountRepository;
    }

    public UserBankAccountDto sendUserMessage(String username) {

        logger.info("Messaggio inviato");

        UserDto userDto = (UserDto) rabbitTemplate.convertSendAndReceive("sendUser", username);

        List<BankAccount> bankAccountList = bankAccountRepository.findByUsername(username);

        List<BankAccountDto> bankAccountDtoList = new ArrayList<>();

        for(BankAccount bankAccount : bankAccountList) {

            bankAccountDtoList.add(bankAccountMapper.toDto(bankAccount));
        }

        UserBankAccountDto userBankAccountDto = new UserBankAccountDto();
        userBankAccountDto.setId(userDto.getId());
        userBankAccountDto.setFirstName(userDto.getFirstName());
        userBankAccountDto.setLastName(userDto.getLastName());
        userBankAccountDto.setBirthDate(userDto.getBirthDate());
        userBankAccountDto.setEmail(userDto.getEmail());
        userBankAccountDto.setBankAccountDtoList(bankAccountDtoList);

        return userBankAccountDto;
    }
}

