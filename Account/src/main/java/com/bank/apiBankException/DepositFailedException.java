package com.bank.apiBankException;

public class DepositFailedException extends Exception{

    public DepositFailedException(String message){
        super(message);
    }
}
