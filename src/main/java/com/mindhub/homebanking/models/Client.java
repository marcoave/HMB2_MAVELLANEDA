package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Client {

    //Atributos o propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String firstName, lastName, email,password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts  =new HashSet<>();

    @OneToMany(fetch=FetchType.EAGER,mappedBy = "client")
    private List<ClientLoan> clientLoans=new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
    private Set<Card> cards=new HashSet<>();


    //Constructores

    public Client(){}
    public Client(String firstName, String lastName, String email,String password){

    this.firstName=firstName;
    this.lastName=lastName;
    this.email=email;
    this.password=password;
}


    //Métodos o comportamientos


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }



    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    /*public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }*/

    public Set<Card> getCards() {
        return cards;
    }

    public void addCard(Card card){
        card.setClient(this);
        this.cards.add(card);
    }
}
