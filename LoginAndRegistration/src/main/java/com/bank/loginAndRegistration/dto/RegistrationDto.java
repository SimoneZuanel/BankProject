package com.bank.loginAndRegistration.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class RegistrationDto {

    @NotBlank(message = "non può essere vuoto!!")
    private String firstName;
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;
    private String email;
    private String password;
}
