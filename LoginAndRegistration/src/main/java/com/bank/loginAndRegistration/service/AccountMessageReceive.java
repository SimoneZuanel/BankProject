package com.bank.loginAndRegistration.service;

import com.bank.loginAndRegistration.dto.LoggerDto;
import com.bank.dto.UserDto;
import com.bank.loginAndRegistration.mapper.LoggerMapper;
import com.bank.loginAndRegistration.mapper.UserMapper;
import com.bank.loginAndRegistration.repository.LoggerRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
