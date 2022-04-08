package com.anz.accountmanagement.service;

import com.anz.accountmanagement.models.Transaction;
import com.anz.accountmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;


    public Transaction getTransaction(Long transactionId) {
        return repository.findById(transactionId)
                .orElse(null);
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return repository.findByAccountAccountNumber(accountNumber);
    }






}
