package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;


   private ClientDTO clientDTO;

    //Probando rutas
    @RequestMapping("/clients/cards")
    //@RequestMapping(path = "/clients/current/cards", method = RequestMethod.GET)
    public List<CardDTO> getCards() {

        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)

    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardColor color, @RequestParam CardType type) {
        Client clientDTO = clientRepository.findByEmail(authentication.getName());

        int cardCount = cardRepository.countCardsByClientEmailAndType(clientDTO.getEmail(), type);
        System.out.println(cardCount);

        if (cardCount>=3) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ya tienes 3 tarjetas de este tipo.");

        }
        // Generar un número de cuenta aleatorio
        Integer cvvNumber = generateCvvNumber();
        String chainNumber=generateChainNumber();

        //Crear tarjeta

        Card cardN=new Card();
        cardN.setColor(color);
        cardN.setType(type);
        cardN.setCardHolder(clientDTO.getFirstName()+" "+clientDTO.getLastName());
        cardN.setFromDate(LocalDate.now());
        cardN.setThruDate(LocalDate.now().plusYears(5));
        cardN.setCvv(cvvNumber);
        cardN.setNumber(chainNumber);

        //Agregar la tarjeta al cliente

        clientDTO.addCard(cardN);

        //Guardar la tarjeta en la base de datos

        cardRepository.save(cardN);

        System.out.println(cardN);


              return new ResponseEntity<>(HttpStatus.CREATED);
  }
    public Integer generateCvvNumber(){

        return getRandomNumber(1,999);

    }
    public String generateChainNumber(){

        return getRandomNumber(1,9999) +"-"+ getRandomNumber(1,9999)+"-"+ getRandomNumber(1,9999)+"-"+ getRandomNumber(1,9999);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);

    }



}
