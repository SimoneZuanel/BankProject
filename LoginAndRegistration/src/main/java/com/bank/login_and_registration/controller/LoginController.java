package com.bank.login_and_registration.controller;

import com.bank.login_and_registration.dto.LoginDto;
import com.bank.login_and_registration.service.AccountMessageSender;
import com.bank.login_and_registration.service.LoginService;
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

    @CrossOrigin("*")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        final String token = this.loginService.createToken(loginDto.getUsername(), loginDto.getPassword());

        return ResponseEntity.ok(this.loginService.verifyToken(token));
    }

}
