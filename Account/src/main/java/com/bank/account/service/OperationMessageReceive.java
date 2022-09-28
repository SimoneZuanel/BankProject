package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.dto.MessageTransactionDto;
import com.bank.account.entity.BankAccount;
import com.bank.account.entity.BankAccountEnum;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationMessageReceive {

    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BankAccountRepository bankAccountRepository;


    @RabbitListener(queues = "withdrawal")
    public String receiveTransactionMessageWithdrawal(MessageTransactionDto messageTransactionDto) {

        BankAccountDto bankAccountDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

       if (bankAccountDto.getState() != BankAccountEnum.ACTIVE) {
            return "failed, account not active!";

        } else if (bankAccountDto.getBalance() > messageTransactionDto.getAmount()) {
            bankAccountDto.setBalance(bankAccountDto.getBalance() - messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));
            return "success";

        } else {
            return "failed";
        }

    }

    @RabbitListener(queues = "deposit")
    public String receiveTransactionMessageDeposit(MessageTransactionDto messageTransactionDto) {

        BankAccountDto bankAccountDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        if (bankAccountDto.getState() != BankAccountEnum.ACTIVE) {
            return "failed, account not active!";

        } else if (messageTransactionDto.getAmount() > 0) {
            bankAccountDto.setBalance(bankAccountDto.getBalance() + messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));
            return "success";

        } else {
            return "failed";
        }

    }

    @RabbitListener(queues = "bankTransfer")
    public String receiveTransactionMessageBankTransfer (MessageTransactionDto messageTransactionDto) {

        BankAccountDto bankPayerDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        BankAccountDto bankBeneficiaryDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanBeneficiary()));


        if (bankPayerDto.getState() != BankAccountEnum.ACTIVE) {
            return "failed, account not active!";

        } else if (bankPayerDto.getBalance() > messageTransactionDto.getAmount() && messageTransactionDto.getAmount() > 0) {
            bankPayerDto.setBalance(bankPayerDto.getBalance() - messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankPayerDto));

            bankBeneficiaryDto.setBalance(bankBeneficiaryDto.getBalance() + messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankBeneficiaryDto));
            return "success";

        } else {
            return "failed";
        }

    }

    @RabbitListener(queues = "ibanList")
    public ArrayList<String> receiveIbanListMessage(String username) {

        List<BankAccount> bankAccountList = bankAccountRepository.findByUsername(username);

        ArrayList<String> ibanList = new ArrayList<>();

        for(BankAccount bankAccount : bankAccountList) {

            ibanList.add(bankAccount.getIban());
        }

        return ibanList;

    }


}
