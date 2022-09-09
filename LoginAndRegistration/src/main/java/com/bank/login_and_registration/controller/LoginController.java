package com.bank.login_and_registration.controller;

import com.bank.login_and_registration.dto.LoggerDto;
import com.bank.login_and_registration.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class LoginController {
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @CrossOrigin("*")
    @PostMapping(value = "/login")
    public String loginClient(@RequestBody LoggerDto loggerDto) {
        return this.loginService.loginClient(loggerDto);
    }

}
