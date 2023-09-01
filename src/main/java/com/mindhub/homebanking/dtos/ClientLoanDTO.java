package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;
    private Double amount;
    private Integer payments;

    private String name;
     private Long loanid;
    //private Long client_id;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
        this.loanid = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public Long getLoanid() {
        return loanid;
    }







}
