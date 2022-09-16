package com.bank.loginAndRegistration.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {

    private String token;

    private List<String> roles;

    public ResponseDto(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }
}
