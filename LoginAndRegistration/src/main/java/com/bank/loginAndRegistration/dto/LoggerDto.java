package com.bank.loginAndRegistration.dto;

import com.bank.loginAndRegistration.entity.User;
import lombok.Data;

@Data
public class LoggerDto {

    private Long id;
    private String username;
    private String password;
    private User userId;
}
