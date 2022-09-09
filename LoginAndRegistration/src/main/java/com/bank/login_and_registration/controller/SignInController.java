package com.bank.login_and_registration.controller;

import com.bank.login_and_registration.dto.LoggerDto;
import com.bank.login_and_registration.dto.UserDto;
import com.bank.login_and_registration.service.AccountMessageSender;
import com.bank.login_and_registration.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SignInController {

    private SignInService signInService;
    private AccountMessageSender accountMessageSender;

    @Autowired
    public SignInController(SignInService signInService, AccountMessageSender accountMessageSender) {

        this.signInService = signInService;
        this.accountMessageSender = accountMessageSender;
    }

    @CrossOrigin("*")
    @PostMapping (value = "/registration")
    public void saveUser(@RequestParam String firstName,
                         @RequestParam String lastName,
                         @RequestParam String birthDate,
                         @RequestParam String email,
                         @RequestParam String password) {

        UserDto userDto = signInService.addUser(firstName, lastName, birthDate, email);
        LoggerDto loggerDto = signInService.addLogger(password, userDto);
        signInService.addAuthority(loggerDto);

        accountMessageSender.sendAccountMessage(loggerDto.getUsername());
    }

}
