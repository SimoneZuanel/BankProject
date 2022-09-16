package com.bank.loginAndRegistration.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {

    private String token;

    private String roles;

    public ResponseDto(String token, String roles) {
        this.token = token;
        this.roles = roles;
    }
}
