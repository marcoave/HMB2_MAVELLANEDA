package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private Double balance;
    private LocalDate creationDate;
    private Set<TransactionDTO> transactions;

    public AccountDTO(){}
    public AccountDTO(Account account){
       this.id= account.getId();
        this.number=account.getNumber();
        this.balance=account.getBalance();
        this.creationDate=account.getCreationDate();
        this.transactions= account.getTransactions().stream().map(element -> new TransactionDTO(element)).collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
