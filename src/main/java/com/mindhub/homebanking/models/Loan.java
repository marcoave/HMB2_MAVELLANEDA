package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.core.SpringVersion;

import javax.persistence.*;
import java.util.ArrayList;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

@Entity
public class Loan {

    //Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount;

    //---
    private Double amount;


    @ElementCollection
    private List<Integer> payments=new ArrayList<>();
    //private Set<Integer> payments=new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "loan")
    //private Set<ClientLoan> clientLoans=new HashSet<>();
    private List<ClientLoan> clientLoans=new ArrayList<>();

    //Constructors
    public Loan() {
    }

    public Loan(Long id, String name, Double maxAmount,Double amount, List<Integer> payments) {
        this.id = id;
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.amount=amount;
    }

    //Métodos
//@JsonIgnore
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
    /*public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }*/

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", payments=" + payments +

                '}';
    }

    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    /*public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }*/
//-----nueva propiedad
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
