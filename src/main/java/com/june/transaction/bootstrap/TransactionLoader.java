package com.june.transaction.bootstrap;

import com.june.transaction.model.Transaction;
import com.june.transaction.repository.TransactionRepository;
import com.june.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TransactionLoader implements CommandLineRunner {
    public final TransactionRepository TransactionRepository;

    public TransactionLoader(TransactionRepository TransactionRepository) {
        this.TransactionRepository = TransactionRepository;
    }

    private void loadTransactions() {
        //load 3 test Transactions when app initialize
        if(TransactionRepository.count() == 0) {
            TransactionRepository.save(Transaction.builder().serialNumber("00001").title("Transaction 1")
                    .description("this is Transaction 1")
                            .amount(1000.20)
                    .build());
            TransactionRepository.save(Transaction.builder().serialNumber("00002").title("Transaction 2")
            .description("this is Transaction 2")
                    .amount(-200.0).build());
            TransactionRepository.save(Transaction.builder().serialNumber("00003").title("Transaction 3")
                    .description("this is Transaction 3")
                    .amount(501.1).build());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.loadTransactions();
    }
}
