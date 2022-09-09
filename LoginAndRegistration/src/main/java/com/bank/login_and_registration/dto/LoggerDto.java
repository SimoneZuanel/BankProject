package com.bank.login_and_registration.dto;

import com.bank.login_and_registration.entity.User;
import lombok.Data;

@Data
public class LoggerDto {

    private Long id;
    private String username;
    private String password;
    private User userId;
}
