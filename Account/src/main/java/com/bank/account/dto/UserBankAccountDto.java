package com.bank.account.dto;

import com.bank.dtoForRabbit.UserRabbitDto;
import lombok.Data;

import java.util.List;

@Data
public class UserBankAccountDto extends UserRabbitDto {

    private List<BankAccountDto> bankAccountDtoList;

}
