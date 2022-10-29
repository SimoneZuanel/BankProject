package com.bank.loginAndRegistration.controller;

import com.bank.apiBankException.LoginFailedException;
import com.bank.loginAndRegistration.dto.LoginDto;
import com.bank.loginAndRegistration.dto.ResponseDto;
import com.bank.loginAndRegistration.serviceRabbit.AccountMessageSender;
import com.bank.loginAndRegistration.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/login")
public class LoginController {
    private LoginService loginService;

    private AccountMessageSender accountMessageSender;


    @Autowired
    public LoginController(LoginService loginService, AccountMessageSender accountMessageSender) {
        this.loginService = loginService;
        this.accountMessageSender = accountMessageSender;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws LoginFailedException {

        final String token = this.loginService.createToken(loginDto.getUsername(), loginDto.getPassword());

        String role = loginService.getRole(loginDto.getUsername());

        return ResponseEntity.ok(new ResponseDto(token, role));
    }

}
