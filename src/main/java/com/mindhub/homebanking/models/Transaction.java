package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.core.SpringVersion;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Transaction {

    //Atributos o propiedades

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")

    private Long id;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

   @ManyToOne (fetch = FetchType.EAGER)
    private Account account;

    //Constructores


    public Transaction() {
    }

    public Transaction(Long id, Double amount, String description, LocalDateTime date, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

//Métodos o comportamientos


    public Long getId() {
        return id;
    }

     public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
