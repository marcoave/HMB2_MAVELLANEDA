package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

public interface TransactionsService {
    void saveTransaction(Transaction transaction);
}
