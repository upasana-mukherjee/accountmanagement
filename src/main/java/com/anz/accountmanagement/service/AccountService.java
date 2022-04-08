package com.anz.accountmanagement.service;


import com.anz.accountmanagement.models.Account;
import com.anz.accountmanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }
}
