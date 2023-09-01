package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName, lastName, email;

    private Set<AccountDTO> accounts;

   // private Set<ClientLoanDTO> loans;
    private List<ClientLoanDTO> loans;
    private Set<CardDTO>cards;
    public ClientDTO(Client client) {

        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client.getAccounts().stream().map(element -> new AccountDTO(element)).collect(Collectors.toSet());
        loans= client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
        //loans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());

    }
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    public Set<AccountDTO>getAccounts(){
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
    /*public Set<ClientLoanDTO> getLoans() {
        return loans;
    }*/

    public Set<CardDTO> getCards() {
        return cards;
    }
}


