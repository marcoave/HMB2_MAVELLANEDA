package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Card {
    //Atributos
@Id
@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
@GenericGenerator(name="native",strategy = "native")
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;


@ManyToOne(fetch = FetchType.EAGER)
    private Client client;

//Construtores

    public Card() {
    }

    public Card(Long id, String cardHolder, CardType type, CardColor color, String number, Integer cvv, LocalDate thruDate, LocalDate fromDate) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;

    }

//Metodos


    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
        this.id = id;
    }*/

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", type=" + type +
                ", color=" + color +
                ", number='" + number + '\'' +
                ", cvv=" + cvv +
                ", thruDate=" + thruDate +
                ", fromDate=" + fromDate +
                '}';
    }
}
