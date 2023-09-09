package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private Long id;
    private String name;
    private Double maxAmount;

    //@ElementCollection

    private List<Integer> payments;

    public LoanDTO (Loan loan){
       id= loan.getId();
       name= loan.getName();
       maxAmount= loan.getMaxAmount();
       payments=loan.getPayments();

   }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
