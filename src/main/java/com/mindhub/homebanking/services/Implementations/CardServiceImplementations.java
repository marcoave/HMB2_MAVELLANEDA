package com.mindhub.homebanking.services.Implementations;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class CardServiceImplementations implements CardService {
    @Autowired
    private CardRepository cardRepository;



    @Override
    public List<CardDTO> getCardsDTO() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @Override
    public int countCardsByClientEmailAndType(String email, CardType cardType) {

      return  cardRepository.countCardsByClientEmailAndType(email, cardType);
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
