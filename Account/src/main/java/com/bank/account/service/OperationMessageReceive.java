package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.dto.MessageTransactionDto;
import com.bank.account.entity.BankAccount;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import com.bank.apiBankException.BankTransferFailedException;
import com.bank.apiBankException.DepositFailedException;
import com.bank.apiBankException.WithdrawalFailedException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationMessageReceive {

    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BankAccountRepository bankAccountRepository;


    @RabbitListener(queues = "withdrawal")
    public String receiveTransactionMessageWithdrawal(MessageTransactionDto messageTransactionDto) throws WithdrawalFailedException {

        BankAccountDto bankAccountDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        if (bankAccountDto == null) {
            throw new WithdrawalFailedException("l'account non esiste");

        } else if (bankAccountDto.getBalance() > messageTransactionDto.getAmount()) {
            bankAccountDto.setBalance(bankAccountDto.getBalance() - messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));
            return "success";

        } else {
            return "failed";
        }

    }

    @RabbitListener(queues = "deposit")
    public String receiveTransactionMessageDeposit(MessageTransactionDto messageTransactionDto) throws DepositFailedException {

        BankAccountDto bankAccountDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        if (bankAccountDto == null) {
            throw new DepositFailedException("l'account non esiste");

        } else if (messageTransactionDto.getAmount() > 0) {
            bankAccountDto.setBalance(bankAccountDto.getBalance() + messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));
            return "success";

        } else {
            return "failed";
        }

    }

    @RabbitListener(queues = "bankTransfer")
    public String receiveTransactionMessageBankTransfer (MessageTransactionDto messageTransactionDto) throws BankTransferFailedException {

        BankAccountDto bankPayerDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        BankAccountDto bankBeneficiaryDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanBeneficiary()));


        if (bankPayerDto == null || bankBeneficiaryDto == null) {
            throw new BankTransferFailedException("un account non è stato trovato");

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
