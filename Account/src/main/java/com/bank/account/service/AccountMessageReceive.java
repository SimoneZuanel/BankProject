package com.bank.account.service;

import com.bank.account.dto.BankAccountDto;
import com.bank.account.dto.MessageTransactionDto;
import com.bank.account.entity.BankAccountEnum;
import com.bank.account.mapper.BankAccountMapper;
import com.bank.account.repository.BankAccountRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountMessageReceive {

    @Autowired
    private UtilAccountService utilAccountService;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionMessageSender transactionMessageSender;

    @RabbitListener(queues = "newAccount")
    public Boolean receiveUserMessage(String username) {
        try {
            BankAccountDto bankAccountDto = new BankAccountDto();
            bankAccountDto.setUsername(username);
            bankAccountDto.setBalance(0.0);
            List<String> numberAccountsList = bankAccountRepository.findNumberAccounts();

            do {
                utilAccountService.generateIban();
            }
            while (numberAccountsList.contains(utilAccountService.getAccountNumber()));

            bankAccountDto.setIban(utilAccountService.getIban());
            bankAccountDto.setNumberAccount(utilAccountService.getAccountNumber());
            bankAccountDto.setState(BankAccountEnum.INACTIVE);

            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));

            return true;

        } catch (Exception e) {
            System.out.println("Error during BankAccount registration");
        }

        return false;
    }

    @RabbitListener(queues = "withdrawalGo")
    public String receiveTransactionMessageWithdrawal(MessageTransactionDto messageTransactionDto) {

        BankAccountDto bankAccountDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        if (bankAccountDto == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account non esiste");
        }

        else if (bankAccountDto.getBalance() > messageTransactionDto.getAmount()) {
            bankAccountDto.setBalance(bankAccountDto.getBalance() - messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));
            return "success";

        } else {
            System.out.println("Il credito non è sufficiente");
            return "failed";
        }

    }

    @RabbitListener(queues = "depositGo")
    public Boolean receiveTransactionMessageDeposit(MessageTransactionDto messageTransactionDto) {

        BankAccountDto bankAccountDto =
                bankAccountMapper.toDto(bankAccountRepository.findByIban(messageTransactionDto.getIbanPayer()));

        if (bankAccountDto == null){
            transactionMessageSender.sendAccountMessageDeposit("Failed");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "l'account non esiste");
        }

        else if (messageTransactionDto.getAmount() > 0) {
            bankAccountDto.setBalance(bankAccountDto.getBalance() + messageTransactionDto.getAmount());
            bankAccountRepository.save(bankAccountMapper.toEntity(bankAccountDto));
            transactionMessageSender.sendAccountMessageDeposit("Success");
            return true;

        } else {
            System.out.println("Il deposito deve essere maggiore di 0");
            transactionMessageSender.sendAccountMessageDeposit("Failed");
            return false;
        }

    }

}
