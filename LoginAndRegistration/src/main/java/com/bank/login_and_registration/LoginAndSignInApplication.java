package com.bank.login_and_registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LoginAndSignInApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginAndSignInApplication.class, args);
    }

}

