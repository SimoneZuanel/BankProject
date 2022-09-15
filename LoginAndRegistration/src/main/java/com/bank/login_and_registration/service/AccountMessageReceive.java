package com.bank.login_and_registration.service;

import com.bank.login_and_registration.dto.LoggerDto;
import com.bank.dto.UserDto;
import com.bank.login_and_registration.mapper.LoggerMapper;
import com.bank.login_and_registration.mapper.UserMapper;
import com.bank.login_and_registration.repository.LoggerRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountMessageReceive {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoggerMapper loggerMapper;
    @Autowired
    private LoggerRepository loggerRepository;


    @RabbitListener(queues = "sendUser")
    public UserDto receiveAccountMessage(String username) {

        LoggerDto loggerDto = loggerMapper.toDto(loggerRepository.findByUsername(username));

        UserDto userDto = userMapper.toDto(loggerDto.getUserId());

        return userDto;

    }
}
