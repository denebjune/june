package com.june.transaction.exception;

public class NoSuchTransactionException extends RuntimeException {
    //no such Transaction exception
    private String message;
    public NoSuchTransactionException(){}
    public NoSuchTransactionException(String message) {
        super(message);
        this.message = message;
    }
}
