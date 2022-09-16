package com.bank.loginAndRegistration.mapper;

import com.bank.loginAndRegistration.dto.AuthorityDto;
import com.bank.loginAndRegistration.entity.Authority;
import org.springframework.stereotype.Component;

@Component
public class AuthorityMapper {

    public AuthorityDto toDto(Authority entity) {
        AuthorityDto dto = new AuthorityDto();
        dto.setUsername(entity.getUsername());
        dto.setAuthority(entity.getAuthority());
        dto.setLoggerId(entity.getLoggerId());
        return dto;
    }

    public Authority toEntity(AuthorityDto dto) {
        Authority entity = new Authority();
        entity.setUsername(dto.getUsername());
        entity.setAuthority(dto.getAuthority());
        entity.setLoggerId(dto.getLoggerId());
        return entity;
    }
}