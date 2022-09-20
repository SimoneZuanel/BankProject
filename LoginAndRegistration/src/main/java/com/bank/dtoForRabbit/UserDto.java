package com.bank.dtoForRabbit;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
}
