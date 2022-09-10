package com.bank.login_and_registration.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegistrationDto {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String password;
}
