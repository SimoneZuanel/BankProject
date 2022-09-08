package com.bank.login_and_registration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AccountMessageSender.class);

    public AccountMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean sendAccountMessage(String username) {

        logger.info("Messaggio inviato");

        Boolean response = (Boolean) rabbitTemplate.convertSendAndReceive("newAccount", username);

        return response;
    }
}



