package com.bank.apiBankException;

public class LoginFailedException extends Exception{

    public LoginFailedException(String message){
        super(message);
    }
}
