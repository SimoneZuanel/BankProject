package com.bank.loginAndRegistration.dto;

import com.bank.loginAndRegistration.entity.AuthorityEnum;
import com.bank.loginAndRegistration.entity.Logger;
import lombok.Data;

import java.util.List;

@Data
public class AuthorityDto {

    private String username;
    private AuthorityEnum authority;
    private Logger loggerId;

}
