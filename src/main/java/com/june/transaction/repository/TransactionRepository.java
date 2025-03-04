package com.june.transaction.repository;

import com.june.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    public Transaction findBySerialNumber(String serialNumber);
    public List<Transaction> findByTitle(String title);
}
