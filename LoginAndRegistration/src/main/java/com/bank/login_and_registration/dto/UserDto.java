package com.bank.login_and_registration.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
}
