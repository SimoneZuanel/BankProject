package com.bank.login_and_registration.dto;

import com.bank.login_and_registration.entity.AuthorityEnum;
import com.bank.login_and_registration.entity.Logger;
import lombok.Data;

@Data
public class AuthorityDto {

    private String username;
    private AuthorityEnum authority;
    private Logger loggerId;

}
