package com.anz.accountmanagement.controller;


import com.anz.accountmanagement.models.Account;
import com.anz.accountmanagement.models.Transaction;
import com.anz.accountmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value="/accounts/{accountNumber}/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;


    @GetMapping(value="/{transactionId}")
    public ResponseEntity<Transaction> findTransaction(@PathVariable Long transactionId) {
        Transaction transaction = service.getTransaction(transactionId);
        transaction.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                        TransactionController.class).findAllTransactionsByAccountNumber(transaction.getAccount().getAccountNumber()))
                .withSelfRel());
        return ResponseEntity.ok(transaction);
    }


   @GetMapping
   public ResponseEntity<CollectionModel<Transaction>> findAllTransactionsByAccountNumber(@PathVariable String accountNumber) {

      List<Transaction> transactions = service.getTransactionsByAccountNumber(accountNumber);
       transactions.forEach(transaction -> {
           transaction.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                   org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                           AccountController.class).findAccountByNumber(accountNumber)).
                   withRel("accounts"));
               });
       Link allTransactionsLink = org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
               org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(
                       TransactionController.class).findAllTransactionsByAccountNumber(accountNumber)).withSelfRel();

       return ResponseEntity.ok(CollectionModel.of(transactions, allTransactionsLink));
   }


}
