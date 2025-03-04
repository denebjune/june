package com.june.transaction.controller;

import com.june.transaction.exception.TransactionAlreadyExistException;
import com.june.transaction.exception.TransactionBadParameterException;
import com.june.transaction.exception.TransactionServerException;
import com.june.transaction.exception.NoSuchTransactionException;
import com.june.transaction.model.Transaction;
import com.june.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions")

    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> Transactions = transactionService.findAll();
        return new ResponseEntity<>(Transactions, HttpStatus.OK);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        try {
            Transaction Transaction = transactionService.findById(id);
            return new ResponseEntity<>(Transaction, HttpStatus.OK);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            throw new NoSuchTransactionException("Transaction with id:" + id + " does not exist");
        }catch(Exception e){
            log.error(e.getMessage());
            throw new TransactionServerException("Transaction system now has some issues");
        }
    }

    @PostMapping(value = "/transaction",consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Transaction> createTransaction(Transaction Transaction) {
        if(Transaction == null){
            throw new TransactionBadParameterException("Transaction is null");
        }
        if(Transaction.getSerialNumber() == null){
            throw new TransactionBadParameterException("Transaction serial number is null");
        }
        if(Transaction.getTitle() == null){
            throw new TransactionBadParameterException("Transaction title is null");
        }
        if(Transaction.getDescription() == null){
            throw new TransactionBadParameterException("Transaction description is null");
        }
        if(Transaction.getAmount() == null){
            throw new TransactionBadParameterException("Transaction amount is null");
        }
        if(transactionService.existBySerialNumber(Transaction.getSerialNumber())){
            throw new TransactionAlreadyExistException("Transaction with serial number:" + Transaction.getSerialNumber() + " already exist");
        }
        try{
            transactionService.addTransaction(Transaction);
            return new ResponseEntity<>(Transaction, HttpStatus.CREATED);
        }catch(Exception e){
            throw new TransactionServerException("Transaction system now has some issues");
        }
    }

    @PutMapping("/transaction/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,Transaction Transaction) {
        if(Transaction.isNull()){
            throw new TransactionBadParameterException("Transaction is null");
        }
        /*if(Transaction.getSerialNumber() == null){
            throw new TransactionBadParameterException("Transaction serial number is null");
        }*/
        if(!transactionService.existById(id)){
            throw new NoSuchTransactionException("Transaction with id:" + id + " does not exist");
        }
        if(Transaction.getSerialNumber() != null){
            Transaction existingTransaction = transactionService.findTransactionBySerialNumber(Transaction.getSerialNumber());
            if(existingTransaction != null && !existingTransaction.getId().equals(id)){
                //already a same serial number exist
                throw new TransactionAlreadyExistException("Transaction with serial number:" + Transaction.getSerialNumber() + " already exist");
            }
        }
        try{
            transactionService.updateTransaction(id,Transaction);
            return new ResponseEntity<>(Transaction, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new TransactionServerException("Transaction system now has some issues");
        }
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        try{
            Transaction transaction = transactionService.findById(id);
            transactionService.deleteById(id);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }catch(NoSuchElementException e){
            log.error(e.getMessage());
            throw new NoSuchTransactionException("Transaction with id:" + id + " does not exist");
        }catch(Exception e){
            log.error(e.getMessage());
            throw new TransactionServerException("Transaction system now has some issues");
        }
    }

}
