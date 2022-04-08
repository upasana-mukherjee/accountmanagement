package com.anz.accountmanagement.controller;


import com.anz.accountmanagement.models.Account;
import com.anz.accountmanagement.models.Transaction;
import com.anz.accountmanagement.service.AccountService;
import com.anz.accountmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/accounts")
public class AccountController {

    @Autowired
    private AccountService service;



    @GetMapping
    public ResponseEntity<CollectionModel<Account>> findAllAccounts() {
        List<Account> accounts = service.getAccounts();
        accounts.forEach(account -> {
            account.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                    org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                            AccountController.class).findAccountByNumber(
                            account.getAccountNumber())).withSelfRel());
            account.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                    org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                            TransactionController.class).findAllTransactionsByAccountNumber(
                                    account.getAccountNumber())).withRel("transactions"));
        });
        Link allAccountsLink = org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                        AccountController.class).findAllAccounts()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(accounts, allAccountsLink));

    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> findAccountByNumber(@PathVariable String accountNumber) {
         Account account = service.getAccountByAccountNumber(accountNumber);
        account.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                        AccountController.class).findAllAccounts()).withRel("accounts"));
                return ResponseEntity.ok(account);
    }



}
