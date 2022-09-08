package com.bank.account.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UtilAccountService {

    final static Integer accountNumberLength = 12;
    private String iban;
    private String accountNumber;


    public String generateAccountNumber(){

        Integer randomAccountNumber = new Random().nextInt((int) Math.pow(10,accountNumberLength));

        return String.format("%0" + accountNumberLength + "d", randomAccountNumber);
    }


    public String generateIban(){

        String cc = "IT";
        String[] cineu = {"60", "14", "91", "88", "36"};
        String[] cin = {"V", "W", "Z", "B"};
        String[] abi = {"05428", "03094", "09636", "00132"};
        String[] cab = {"11101", "80606", "99754", "23422"};

        Random random = new Random();

        StringBuilder iban = new StringBuilder();
        iban.append(cc);
        iban.append(cineu[random.nextInt(5)]);
        iban.append(cin[random.nextInt(4)]);
        iban.append(abi[random.nextInt(4)]);
        iban.append(cab[random.nextInt(4)]);

        String accountNumber = generateAccountNumber();
        iban.append(accountNumber);
        this.accountNumber = accountNumber;
        this.iban = iban.toString();

        return this.iban;
    }

    protected String getAccountNumber() {
        return accountNumber;
    }

    protected String getIban() {
        return iban;
    }


}
