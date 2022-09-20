package com.bank.loginAndRegistration.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class RegistrationDto {

    @NotBlank(message = "Tutti i campi devono essere inseriti")
    private String firstName;

    @NotBlank(message = "Tutti i campi devono essere inseriti")
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;

    @NotBlank(message = "Tutti i campi devono essere inseriti")
    @Email(regexp = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[AZ]{2,4}\\b")
    private String email;

    @NotBlank(message = "Tutti i campi devono essere inseriti")
    private String password;
}
