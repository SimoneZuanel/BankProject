package com.bank.account.mapper;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccountDto toDto(BankAccount entity) {
        BankAccountDto dto = new BankAccountDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setIban(entity.getIban());
        dto.setBalance(entity.getBalance());
        dto.setNumberAccount(entity.getNumberAccount());
        dto.setState(entity.getState());
        return dto;
    }

    public BankAccount toEntity(BankAccountDto dto) {
        BankAccount entity = new BankAccount();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setIban(dto.getIban());
        entity.setBalance(dto.getBalance());
        entity.setNumberAccount(dto.getNumberAccount());
        entity.setState(dto.getState());
        return entity;
    }
}