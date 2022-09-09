package com.bank.transaction.service;

import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.mapper.TransactionMapper;
import com.bank.transaction.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageSender {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    private final RabbitTemplate rabbitTemplate;


    private static final Logger logger = LoggerFactory.getLogger(AccountMessageSender.class);

    public AccountMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public Boolean sendAccountMessageWithdrawal(TransactionDto transactionDto) {

        logger.info("Messaggio inviato");

        String response = (String) rabbitTemplate.convertSendAndReceive("withdrawal", transactionDto);

        TransactionDto newTransactionDto = transactionMapper.toDto(transactionRepository.findByState("loading"));

        newTransactionDto.setState(response);

        transactionRepository.save(transactionMapper.toEntity(newTransactionDto));

        return true;
    }

    public Boolean sendAccountMessageDeposit(TransactionDto transactionDto) {

        logger.info("Messaggio inviato");

        String response = (String) rabbitTemplate.convertSendAndReceive("deposit", transactionDto);

        TransactionDto newTransactionDto = transactionMapper.toDto(transactionRepository.findByState("loading"));

        newTransactionDto.setState(response);

        transactionRepository.save(transactionMapper.toEntity(newTransactionDto));

        return true;
    }


}
