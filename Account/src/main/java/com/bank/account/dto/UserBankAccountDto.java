package com.bank.account.dto;

import com.bank.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class UserBankAccountDto extends UserDto {

    private List<BankAccountDto> bankAccountDtoList;

}
