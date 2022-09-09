package com.bank.transaction.mapper;

import com.bank.transaction.dto.TransactionDto;
import com.bank.transaction.entity.Transaction;
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
        return entity;
    }
}