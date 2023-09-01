package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    //Attributes or properties
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private Long id;
    private String number;
    private Double balance;
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();

    //Constructors

    public Account() {
    }

    public Account(String number, Double balance, LocalDate creationDate) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    //Methods or behaviors


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    //@JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        this.transactions.add(transaction);
}

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                ", client=" + client +
                ", transactions=" + transactions +
                '}';
    }
}
