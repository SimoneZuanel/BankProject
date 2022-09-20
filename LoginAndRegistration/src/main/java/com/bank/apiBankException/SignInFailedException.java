package com.bank.apiBankException;

public class SignInFailedException extends Exception {

    public SignInFailedException(String message) {
        super(message);
    }
}
