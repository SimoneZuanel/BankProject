package com.bank.login_and_registration.controller;

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

    @PostMapping(value = "/loginClient")
    public String loginClient(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        return this.loginService.loginClient(username, password);
    }

    @PostMapping(value = "/loginEmployee")
    public String loginEmployee(@RequestParam("email") String email,
                              @RequestParam("password") String password) {
        return this.loginService.loginEmployee(email, password);
    }

}
