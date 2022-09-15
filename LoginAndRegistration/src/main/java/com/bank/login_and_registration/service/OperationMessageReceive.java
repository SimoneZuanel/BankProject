package com.bank.login_and_registration.service;

import com.bank.dto.UserDto;
import com.bank.login_and_registration.entity.User;
import com.bank.login_and_registration.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationMessageReceive {

    @Autowired
    private UserRepository userRepository;


    @RabbitListener(queues = "userList")
    public ArrayList receiveUserListMessage(ArrayList userListDto) {

        List<User> userList = userRepository.findAllByOrderByFirstNameAscLastNameAsc();

        for(User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setBirthDate(user.getBirthDate());
            userDto.setEmail(user.getEmail());
            userListDto.add(userDto);
        }

        return userListDto;

    }

}
