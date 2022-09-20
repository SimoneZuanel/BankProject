package com.bank.apiBankException;

public class BankTransferFailedException extends Exception{

    public BankTransferFailedException(String message){
        super(message);
    }
}
