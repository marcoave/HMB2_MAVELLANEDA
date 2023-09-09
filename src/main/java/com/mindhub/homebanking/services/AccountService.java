package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsDTO();
}