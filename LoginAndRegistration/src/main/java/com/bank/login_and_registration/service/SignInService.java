package com.bank.login_and_registration.service;

import com.bank.login_and_registration.dto.AuthorityDto;
import com.bank.login_and_registration.dto.LoggerDto;
import com.bank.login_and_registration.dto.UserDto;
import com.bank.login_and_registration.entity.AuthorityEnum;
import com.bank.login_and_registration.mapper.AuthorityMapper;
import com.bank.login_and_registration.mapper.LoggerMapper;
import com.bank.login_and_registration.mapper.UserMapper;
import com.bank.login_and_registration.repository.AuthorityRepository;
import com.bank.login_and_registration.repository.LoggerRepository;
import com.bank.login_and_registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignInService {

    final static Integer usernameLength = 7;

    @Autowired UserRepository userRepository;
    @Autowired UserMapper userMapper;
    @Autowired LoggerRepository loggerRepository;
    @Autowired LoggerMapper loggerMapper;
    @Autowired AuthorityMapper authorityMapper;
    @Autowired AuthorityRepository authorityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public UserDto addUser(String firstName, String lastName, String birthDate, String email) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setBirthDate(birthDate);
        userDto.setEmail(email);
        return userDto;
    }

    public LoggerDto addLogger(String password, UserDto userDto) {
        LoggerDto loggerDto = new LoggerDto();
        loggerDto.setUsername(generateUsername());
        loggerDto.setPassword(passwordEncoder.encode(password));
        loggerDto.setUserId(userMapper.toEntity(userDto));
        return loggerDto;
    }

    public void addAuthority(LoggerDto loggerDto) {
        AuthorityDto authorityDto = new AuthorityDto();
        authorityDto.setAuthority(AuthorityEnum.ROLE_CLIENT);
        authorityDto.setUsername(loggerDto.getUsername());
        authorityDto.setLoggerId(loggerMapper.toEntity(loggerDto));
        authorityRepository.save(authorityMapper.toEntity(authorityDto));
    }

    public String generateUsername() {
        List<Integer> usernameList = loggerRepository.findUsername();
        Integer userInt = randomUsernameGenerate();

        while (usernameList.contains(userInt)) {
            userInt = randomUsernameGenerate();
        }

        return String.format("%0" + usernameLength + "d", userInt);
    }

    public Integer randomUsernameGenerate() {
        return new Random().nextInt((int) Math.pow(10,usernameLength));
    }
}
