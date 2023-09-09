package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class LoanApplicationDTO {


    private Long loanId;

    private String loanType;
    private Double amount;

    private Integer payments;
    private  String accountToNumber;


   public LoanApplicationDTO (){

   }
    public LoanApplicationDTO (Long loanId, Double amount, Integer payments, String accountToNumber){
       this.loanId= loanId;
        this.amount= amount;
        this.payments=payments;
        this.accountToNumber=accountToNumber;

    }


    public Long getLoanId() {
        return loanId;
    }


    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }


    public String getAccountToNumber() {
        return accountToNumber;
    }



}
