package com.bank.loginAndRegistration.controller;

import com.bank.apiBankException.SignInFailedException;
import com.bank.loginAndRegistration.dto.LoggerDto;
import com.bank.loginAndRegistration.dto.RegistrationDto;
import com.bank.dtoForRabbit.UserRabbitDto;
import com.bank.loginAndRegistration.dto.UserDto;
import com.bank.loginAndRegistration.serviceRabbit.AccountMessageSender;
import com.bank.loginAndRegistration.service.SignInService;
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


    @PostMapping (value = "/registration")
    public void saveUser(@RequestBody @Valid RegistrationDto registrationDto) throws SignInFailedException {

        UserDto userDto = signInService.addUser(registrationDto.getFirstName(), registrationDto.getLastName(),
                registrationDto.getBirthDate(), registrationDto.getEmail(), registrationDto.getPassword());

        accountMessageSender.sendNewAccountMessage(userDto.getUsername());
    }

}
