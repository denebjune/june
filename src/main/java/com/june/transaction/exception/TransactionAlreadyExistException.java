package com.june.transaction.exception;

public class TransactionAlreadyExistException extends RuntimeException {
    private String message;
    public TransactionAlreadyExistException(){}
    public TransactionAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
