package com.bank.transaction.service;

import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.mapper.TransactionMapper;
import com.bank.transaction.repository.TransactionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionMessageReceive {

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionRepository transactionRepository;


    @RabbitListener(queues = "withdrawalReturn")
    public Boolean receiveAccountMessageWithdrawal(String state) {

        TransactionDto transactionDto = transactionMapper.toDto(transactionRepository.findByState(""));

        transactionDto.setState(state);

        transactionRepository.save(transactionMapper.toEntity(transactionDto));

        return true;
    }

    @RabbitListener(queues = "depositReturn")
    public Boolean receiveAccountMessageDeposit(String state) {

        TransactionDto transactionDto = transactionMapper.toDto(transactionRepository.findByState(""));

        transactionDto.setState(state);

        transactionRepository.save(transactionMapper.toEntity(transactionDto));

        return true;
    }

}
