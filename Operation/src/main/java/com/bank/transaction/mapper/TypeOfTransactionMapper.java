package com.bank.transaction.mapper;

import com.bank.transaction.dto.TypeOfTransactionDto;
import com.bank.transaction.entity.TypeOfTransaction;
import org.springframework.stereotype.Component;

@Component
public class TypeOfTransactionMapper {

    public TypeOfTransactionDto toDto(TypeOfTransaction entity) {
        TypeOfTransactionDto dto = new TypeOfTransactionDto();
        dto.setId(entity.getId());
        dto.setTypeOfTransactionEnum(entity.getTypeOfTransactionEnum());
        dto.setTransactionId(entity.getTransactionId());
        return dto;
    }

    public TypeOfTransaction toEntity(TypeOfTransactionDto dto) {
        TypeOfTransaction entity = new TypeOfTransaction();
        entity.setId(dto.getId());
        entity.setTypeOfTransactionEnum(dto.getTypeOfTransactionEnum());
        entity.setTransactionId(dto.getTransactionId());
        return entity;
    }
}
