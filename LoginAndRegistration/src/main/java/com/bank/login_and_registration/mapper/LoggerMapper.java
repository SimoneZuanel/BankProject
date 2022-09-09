package com.bank.login_and_registration.mapper;

import com.bank.login_and_registration.dto.LoggerDto;
import com.bank.login_and_registration.entity.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerMapper {

    public LoggerDto toDto(Logger entity) {
        LoggerDto dto = new LoggerDto();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public Logger toEntity(LoggerDto dto) {
        Logger entity = new Logger();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}