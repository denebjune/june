package com.june.transaction.exception;

public class TransactionBadParameterException extends RuntimeException{
    private String message;

    public TransactionBadParameterException(String message){
        super(message);
        this.message = message;
    }
}
