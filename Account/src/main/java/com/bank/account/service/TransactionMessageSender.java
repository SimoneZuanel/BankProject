package com.bank.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionMessageSender.class);

    public TransactionMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public Boolean sendAccountMessageWithdrawal(String state) {

        logger.info("Messaggio inviato");

        Boolean response = (Boolean) rabbitTemplate.convertSendAndReceive("WithdrawalReturn", state);

        return response;
    }

    public Boolean sendAccountMessageDeposit(String state) {

        logger.info("Messaggio inviato");

        Boolean response = (Boolean) rabbitTemplate.convertSendAndReceive("depositReturn", state);

        return response;
    }

}
