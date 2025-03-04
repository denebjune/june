package com.june.transaction.service;

import com.june.transaction.model.Transaction;
import com.june.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceTmpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findByTitle(String title) {
        List<Transaction> Transactions = new ArrayList<>();
        transactionRepository.findByTitle(title).forEach(Transactions::add);
        return Transactions;
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void updateTransaction(Long id, Transaction Transaction) {
        Transaction existingTransaction = transactionRepository.findById(id).get();
        if(Transaction.getTitle() != null) {
            existingTransaction.setTitle(Transaction.getTitle());
        }
        if(Transaction.getDescription() != null) {
            existingTransaction.setDescription(Transaction.getDescription());
        }
        if(Transaction.getAmount() != null) {
            existingTransaction.setAmount(Transaction.getAmount());
        }
        if(Transaction.getSerialNumber() != null){
            existingTransaction.setSerialNumber(Transaction.getSerialNumber());
        }
        transactionRepository.save(existingTransaction);
    }

    @Override
    public Transaction addTransaction(Transaction Transaction) {
        return transactionRepository.save(Transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> Transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(Transactions::add);
        return Transactions;
    }

    @Override
    public Boolean existBySerialNumber(String serialNumber) {
        Transaction Transaction = transactionRepository.findBySerialNumber(serialNumber);
        return Transaction != null;
    }

    @Override
    public Boolean existById(Long id) {
       return transactionRepository.existsById(id);
    }

    @Override
    public Transaction findTransactionBySerialNumber(String serialNumber) {
        return transactionRepository.findBySerialNumber(serialNumber);
    }


}
