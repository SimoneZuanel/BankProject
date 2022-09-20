package com.bank.apiBankException;

public class AccountOpeningFailed extends Exception {
    public AccountOpeningFailed(String message) {
        super(message);
    }
}
