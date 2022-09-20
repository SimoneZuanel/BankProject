package com.bank.apiBankException;

public class AccountClosureFailedException extends Exception{
    public AccountClosureFailedException(String message) {
        super(message);
    }
}
