package com.anz.accountmanagement.models;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction extends RepresentationModel<Transaction> {
    @Id
    @GeneratedValue
    private Long id;

    private String valueDate;
    private Double debitAmount;
    private Double creditAmount;
    private String debitOrCredit;
    private String transactionNarrative;
    @ManyToOne
    private Account account;


    public Transaction(){

    }

    public Transaction(Long id, String valueDate, Double debitAmount, Double creditAmount, String debitOrCredit, String transactionNarrative, Account account) {
        this.id = id;
        this.valueDate = valueDate;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.debitOrCredit = debitOrCredit;
        this.transactionNarrative = transactionNarrative;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
