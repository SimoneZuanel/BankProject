package com.bank.loginAndRegistration.service;

import com.bank.loginAndRegistration.dto.AuthorityDto;
import com.bank.loginAndRegistration.dto.LoggerDto;
import com.bank.dto.UserDto;
import com.bank.loginAndRegistration.entity.AuthorityEnum;
import com.bank.loginAndRegistration.mapper.AuthorityMapper;
import com.bank.loginAndRegistration.mapper.LoggerMapper;
import com.bank.loginAndRegistration.mapper.UserMapper;
import com.bank.loginAndRegistration.repository.AuthorityRepository;
import com.bank.loginAndRegistration.repository.LoggerRepository;
import com.bank.loginAndRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
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
    @Autowired PasswordEncoder passwordEncoder;


    public UserDto addUser(String firstName, String lastName, Date birthDate, String email) {

        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sm.format(birthDate);

        if(firstName == null || lastName == null || birthDate == null || email == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutti i campi devono essere riempiti");

        StringTokenizer st = new StringTokenizer(strDate, "/");
        Integer day= Integer.parseInt(st.nextToken());
        if(day==null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Formato sbagliato");
        Integer month =  Integer.parseInt(st.nextToken());
        if(month==null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Formato sbagliato");
        Integer year =  Integer.parseInt(st.nextToken());
        if(year==null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Formato sbagliato");
        if(year>2004)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hai meno di 18 anni");
        if((month==2) && (day<1 || day >29))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Giorno non corretto");
        if((month==1 || month==3 || month==5 || month == 7 || month == 8 || month == 10
                || month==12) && (day<1 || day > 31))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Giorno non corretto");
        if((month==4 || month== 6 || month==9 || month==11) && (day<1 || day > 30))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Giorno non corretto");

        // email controllo caratteri
        if (userRepository.findByEmail(email) != null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Email già esistente");


        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setBirthDate(strDate);
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
        List<Integer> usernameList = loggerRepository.findUsernames();
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