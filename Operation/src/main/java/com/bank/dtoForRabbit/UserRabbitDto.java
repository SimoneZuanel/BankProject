package com.bank.dtoForRabbit;

import lombok.Data;

@Data
public class UserRabbitDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
}
