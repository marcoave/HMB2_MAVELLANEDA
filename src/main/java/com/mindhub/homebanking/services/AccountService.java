package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsDTO();

    void saveAccount(Account account);

    Account findById (Long id);

    Account findByNumber(String account);
    List <AccountDTO> getAccountCurrentDTO(Client client);
}