package com.bank.login_and_registration.dto;

import lombok.Data;

@Data
public class RegistrationDto {

    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String password;
}
