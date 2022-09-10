package com.bank.operation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginAndRegistrationMessageSender {

    private final RabbitTemplate rabbitTemplate;


    private static final Logger logger = LoggerFactory.getLogger(LoginAndRegistrationMessageSender.class);

    public LoginAndRegistrationMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ArrayList sendUserListMessage(ArrayList userList) {

        logger.info("Messaggio inviato");

        return (ArrayList) rabbitTemplate.convertSendAndReceive("userList", userList);
    }

}
