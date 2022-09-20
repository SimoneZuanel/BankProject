package com.bank.account.dto;

import com.bank.dtoForRabbit.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class UserBankAccountDto extends UserDto {

    private List<BankAccountDto> bankAccountDtoList;

}
