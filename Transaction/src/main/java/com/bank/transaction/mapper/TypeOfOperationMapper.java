package com.bank.transaction.mapper;

import com.bank.transaction.dto.TypeOfOperationDto;
import com.bank.transaction.entity.TypeOfOperation;
import org.springframework.stereotype.Component;

@Component
public class TypeOfOperationMapper {

    public TypeOfOperationDto toDto(TypeOfOperation entity) {
        TypeOfOperationDto dto = new TypeOfOperationDto();
        dto.setId(entity.getId());
        dto.setTypeOfOperation(entity.getTypeOfOperation());
        dto.setTransactionId(entity.getTransactionId());
        return dto;
    }

    public TypeOfOperation toEntity(TypeOfOperationDto dto) {
        TypeOfOperation entity = new TypeOfOperation();
        entity.setId(dto.getId());
        entity.setTypeOfOperation(dto.getTypeOfOperation());
        entity.setTransactionId(dto.getTransactionId());
        return entity;
    }
}
