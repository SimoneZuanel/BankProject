package com.bank.apiBankException;

public class WithdrawalFailedException extends Exception{

    public WithdrawalFailedException(String message){
        super(message);
    }
}
