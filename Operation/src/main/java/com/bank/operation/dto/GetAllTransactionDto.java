package com.bank.operation.dto;

import lombok.Data;

@Data
public class GetAllTransactionDto {

    String username;
    String startDate;
    String endDate;
}
