package com.bank.login_and_registration.controller;

import com.bank.login_and_registration.dto.LoggerDto;
import com.bank.login_and_registration.dto.RegistrationDto;
import com.bank.dto.UserDto;
import com.bank.login_and_registration.service.AccountMessageSender;
import com.bank.login_and_registration.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
@Validated
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
    public void saveUser(@RequestBody @Valid RegistrationDto registrationDto) {

        UserDto userDto = signInService.addUser(registrationDto.getFirstName(), registrationDto.getLastName(),
                registrationDto.getBirthDate(), registrationDto.getEmail());
        LoggerDto loggerDto = signInService.addLogger(registrationDto.getPassword(), userDto);
        signInService.addAuthority(loggerDto);

        accountMessageSender.sendNewAccountMessage(loggerDto.getUsername());
    }

    @CrossOrigin("*")
    @PostMapping (value = "/registration-param")
    public void saveUser(@RequestParam String firstName, @RequestParam String lastName) {

    }

}
