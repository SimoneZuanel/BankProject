package com.bank.loginAndRegistration.controller;

import com.bank.dto.UserDto;
import com.bank.loginAndRegistration.dto.AuthorityDto;
import com.bank.loginAndRegistration.dto.LoginDto;
import com.bank.loginAndRegistration.dto.ResponseDto;
import com.bank.loginAndRegistration.entity.AuthorityEnum;
import com.bank.loginAndRegistration.service.AccountMessageSender;
import com.bank.loginAndRegistration.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        final String token = this.loginService.createToken(loginDto.getUsername(), loginDto.getPassword());

        ArrayList<String> roles = loginService.getRoles(loginDto.getUsername());

        return ResponseEntity.ok(new ResponseDto(token, roles.stream().collect(Collectors.joining(","))));
    }

}
