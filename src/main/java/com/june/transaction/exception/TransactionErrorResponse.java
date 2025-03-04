package com.june.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionErrorResponse {
    private String message;
    private int code;
    public TransactionErrorResponse(String message,int code) {
        super();
        this.message = message;
        this.code = code;
    }
}
