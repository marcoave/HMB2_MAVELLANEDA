package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {

    List<CardDTO> getCardsDTO();

    int countCardsByClientEmailAndType(String email, CardType cardType);

    Card findByNumber(String number);
    void saveCard(Card card);
}
