package com.june.transaction.controller;

import com.june.transaction.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TransactionExceptionController {
    static int NotExisted = 1004;
    static int Conflict = 1003;
    static int BadRequest = 1002;
    public int ServerError = 1005;
    @ExceptionHandler(value = NoSuchTransactionException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TransactionErrorResponse handleNoSuchTransactionException(NoSuchTransactionException e){
        return new TransactionErrorResponse(e.getMessage(),NotExisted);
    }
    @ExceptionHandler(value = TransactionAlreadyExistException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TransactionErrorResponse handleTransactionAlreadyExistException(TransactionAlreadyExistException e){
        return new TransactionErrorResponse(e.getMessage(),Conflict);
    }

    @ExceptionHandler(value = TransactionServerException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TransactionErrorResponse handleTransactionServerException(TransactionServerException e){
        return new TransactionErrorResponse(e.getMessage(),ServerError);
    }

    @ExceptionHandler(value = TransactionBadParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TransactionErrorResponse handleTransactionBadParameterException(TransactionBadParameterException e){
        return new TransactionErrorResponse(e.getMessage(),BadRequest);
    }
}
