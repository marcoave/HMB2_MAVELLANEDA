package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.core.SpringVersion;

import javax.persistence.*;

@Entity
public class ClientLoan {

//Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private Double amount;
    private Integer payments;

    @ManyToOne (fetch = FetchType.EAGER)
    private Client client;

    @ManyToOne (fetch = FetchType.EAGER)
    private Loan loan;

    //Constructores

    public ClientLoan() {
    }

    public ClientLoan(Long id, Double amount, Integer payments, Client client, Loan loan) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }
//Metodos


    public Long getId() {
        return id;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
