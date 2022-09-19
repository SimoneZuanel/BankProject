package com.bank.loginAndRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LoginAndRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginAndRegistrationApplication.class, args);
    }

}

