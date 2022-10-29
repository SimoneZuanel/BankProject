package com.bank.loginAndRegistration.service;

import com.bank.apiBankException.SignInFailedException;
import com.bank.loginAndRegistration.dto.UserDto;
import com.bank.loginAndRegistration.enumeration.AuthorityEnum;
import com.bank.loginAndRegistration.mapper.UserMapper;
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
    @Autowired PasswordEncoder passwordEncoder;


    public UserDto addUser(String firstName, String lastName, Date birthDate, String email, String password) throws SignInFailedException {

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
        userDto.setUsername(generateUsername());
        userDto.setPassword(passwordEncoder.encode(password));
        userDto.setAuthority(AuthorityEnum.ROLE_CLIENT);

        userRepository.save(userMapper.toEntity(userDto));

        return userDto;
    }

    public String generateUsername() {
        List<String> usernameList = userRepository.findUsernames();
        Integer usernameNumber = randomUsernameGenerate();

        while (usernameList.contains(usernameNumber)) {
            usernameNumber = randomUsernameGenerate();
        }

        return String.format("%0" + usernameLength + "d", usernameNumber);
    }

    public Integer randomUsernameGenerate() {
        return new Random().nextInt((int) Math.pow(10,usernameLength));
    }
}
