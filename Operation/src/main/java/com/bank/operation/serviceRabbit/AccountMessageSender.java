package com.bank.operation.serviceRabbit;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.entity.Transaction;
import com.bank.operation.mapper.TransactionMapper;
import com.bank.operation.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Boolean sendAccountMessageBankTransfer(TransactionDto transactionDto) {

        logger.info("Messaggio inviato");

        String response = (String) rabbitTemplate.convertSendAndReceive("bankTransfer", transactionDto);

        List<Transaction> newTransactions = transactionRepository.findAllByState("loading");

        List<TransactionDto> newTransactionsDto = new ArrayList<>();

        for (Transaction newTransaction : newTransactions ){
            newTransactionsDto.add(transactionMapper.toDto(newTransaction));
        }

        for (TransactionDto newTransactionDto : newTransactionsDto ){
            newTransactionDto.setState(response);
            transactionRepository.save(transactionMapper.toEntity(newTransactionDto));
        }

        return true;
    }

    public ArrayList<String> sendIbanListMessage(String username) {

        logger.info("Messaggio inviato");

        return (ArrayList<String>) rabbitTemplate.convertSendAndReceive("ibanList", username);
    }

}
