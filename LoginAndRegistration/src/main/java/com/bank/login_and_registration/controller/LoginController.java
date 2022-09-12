package com.bank.login_and_registration.controller;

import com.bank.login_and_registration.dto.LoginDto;
import com.bank.login_and_registration.jwt.JwtProvider;
import com.bank.login_and_registration.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class LoginController {
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /*@CrossOrigin("*")
    @PostMapping(value = "/login")
    public String login(@RequestBody LoginDto loginDto) {
        return this.loginService.login(loginDto.getUsername(), loginDto.getPassword());
    }*/

    @CrossOrigin("*")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        final String token = this.loginService.createToken(loginDto.getUsername(), loginDto.getPassword());

        return ResponseEntity.ok(this.loginService.verifyToken(token));
    }

}
