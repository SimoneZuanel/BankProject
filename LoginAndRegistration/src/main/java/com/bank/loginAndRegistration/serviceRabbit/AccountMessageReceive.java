package com.bank.loginAndRegistration.serviceRabbit;

import com.bank.dtoForRabbit.UserRabbitDto;
import com.bank.loginAndRegistration.dto.UserDto;
import com.bank.loginAndRegistration.mapper.UserMapper;
import com.bank.loginAndRegistration.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageReceive {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;


    @RabbitListener(queues = "sendUser")
    public UserRabbitDto receiveAccountMessage(String username) {

        UserDto userDto = userMapper.toDto(userRepository.findByUsername(username));

        UserRabbitDto userRabbitDto = new UserRabbitDto();
        userRabbitDto.setId(userDto.getId());
        userRabbitDto.setFirstName(userDto.getFirstName());
        userRabbitDto.setLastName(userDto.getLastName());
        userRabbitDto.setBirthDate(userDto.getBirthDate());
        userRabbitDto.setEmail(userDto.getEmail());

        return userRabbitDto;

    }
}
