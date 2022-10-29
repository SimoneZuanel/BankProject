package com.bank.operation.mapper;

import com.bank.operation.dto.TransactionDto;
import com.bank.operation.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto toDto(Transaction entity) {
        TransactionDto dto = new TransactionDto();
        dto.setId(entity.getId());
        dto.setIbanPayer(entity.getIbanPayer());
        dto.setIbanBeneficiary(entity.getIbanBeneficiary());
        dto.setAmount(entity.getAmount());
        dto.setDate(entity.getDate());
        dto.setCausal(entity.getCausal());
        dto.setState(entity.getState());
        dto.setTypeOfTransactionEnum(entity.getTypeOfTransactionEnum());
        return dto;
    }

    public Transaction toEntity(TransactionDto dto) {
        Transaction entity = new Transaction();
        entity.setId(dto.getId());
        entity.setIbanPayer(dto.getIbanPayer());
        entity.setIbanBeneficiary(dto.getIbanBeneficiary());
        entity.setAmount(dto.getAmount());
        entity.setDate(dto.getDate());
        entity.setCausal(dto.getCausal());
        entity.setState(dto.getState());
        entity.setTypeOfTransactionEnum(dto.getTypeOfTransactionEnum());
        return entity;
    }
}