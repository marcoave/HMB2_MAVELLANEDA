package com.mindhub.homebanking.dtos;


public class LoanApplicationDTO {


    private long loanId;

    private String loanType;
    private Double amount;

    private Integer payments;
    private  String accountToNumber;


   public LoanApplicationDTO (){

   }
    public LoanApplicationDTO (long loanId, Double amount, Integer payments, String accountToNumber){
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
