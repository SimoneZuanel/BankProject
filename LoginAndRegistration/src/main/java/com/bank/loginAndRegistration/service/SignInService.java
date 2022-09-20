package com.bank.loginAndRegistration.service;

import com.bank.apiBankException.SignInFailedException;
import com.bank.loginAndRegistration.dto.AuthorityDto;
import com.bank.loginAndRegistration.dto.LoggerDto;
import com.bank.dtoForRabbit.UserDto;
import com.bank.loginAndRegistration.entity.AuthorityEnum;
import com.bank.loginAndRegistration.mapper.AuthorityMapper;
import com.bank.loginAndRegistration.mapper.LoggerMapper;
import com.bank.loginAndRegistration.mapper.UserMapper;
import com.bank.loginAndRegistration.repository.AuthorityRepository;
import com.bank.loginAndRegistration.repository.LoggerRepository;
import com.bank.loginAndRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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


    public UserDto addUser(String firstName, String lastName, Date birthDate, String email) throws SignInFailedException {

        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sm.format(birthDate);

        LocalDate currentDate = LocalDate.now();
        Integer currentYear = currentDate.getYear();
        Integer currentMonth= currentDate.getMonthValue();
        Integer currentDay= currentDate.getDayOfMonth();


        StringTokenizer st = new StringTokenizer(strDate, "/");
        Integer day= Integer.parseInt(st.nextToken());
        if(day==null)
            throw new SignInFailedException("Formato Sbagliato");
        Integer month =  Integer.parseInt(st.nextToken());
        if(month==null)
            throw new SignInFailedException("Formato Sbagliato");
        Integer year =  Integer.parseInt(st.nextToken());
        if(year==null)
            throw new SignInFailedException("Formato Sbagliato");
        if(currentYear - year < 18)
            throw new SignInFailedException("Hai meno di 18 anni");
        if((currentYear - year)== 18 && ((currentMonth < month) || ((currentMonth== month) && currentDay<day)))
            throw new SignInFailedException("Hai meno di 18 anni");
        if((month==2) && (day<1 || day >29))
            throw new SignInFailedException("Giorno non corretto");
        if((month==1 || month==3 || month==5 || month == 7 || month == 8 || month == 10
                || month==12) && (day<1 || day > 31))
            throw new SignInFailedException("Giorno non corretto");
        if((month==4 || month== 6 || month==9 || month==11) && (day<1 || day > 30))
            throw new SignInFailedException("Giorno non corretto");


        if (userRepository.findByEmail(email) != null)
            throw new SignInFailedException("Email già esistente");


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
