package com.bank.loginAndRegistration.dto;

import com.bank.loginAndRegistration.enumeration.AuthorityEnum;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String username;
    private String password;
    private AuthorityEnum authority;
}
