package com.bank.account.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserAccountService {

    final static Integer accountNumberLength = 12;
    private String accountNumber = generateAccountNumber();


    public String generateIban(){

        String[] cc = {"IT", "FR", "NL"};
        String[] cineu = {"60", "14", "91", "88", "36"};
        String[] cin = {"V", "W", "Z", "B"};
        String[] abi = {"05428", "03094", "09636", "00132"};
        String[] cab = {"11101", "80606", "99754", "23422"};

        Random random = new Random();

        StringBuilder iban = new StringBuilder();
        iban.append(cc[random.nextInt(3)]);
        iban.append(cineu[random.nextInt(5)]);
        iban.append(cin[random.nextInt(4)]);
        iban.append(abi[random.nextInt(4)]);
        iban.append(cab[random.nextInt(4)]);
        iban.append(getAccountNumber());

        return iban.toString();
    }

    public String generateAccountNumber(){

        Integer randomAccountNumber = new Random().nextInt((int) Math.pow(10,accountNumberLength));

        return accountNumber = String.format("%0" + accountNumberLength + "d", randomAccountNumber);
    }

    protected String getAccountNumber() {
        return accountNumber;
    }

}
