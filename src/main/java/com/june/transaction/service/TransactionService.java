package com.june.transaction.service;

import com.june.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findByTitle(String title);
    Transaction findById(Long id);
    void updateTransaction(Long id,Transaction Transaction);
    Transaction addTransaction(Transaction Transaction);
    void deleteById(Long id);
    List<Transaction> findAll();
    Boolean existBySerialNumber(String serialNumber);
    Boolean existById(Long id);
    Transaction findTransactionBySerialNumber(String serialNumber);
}
