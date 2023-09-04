package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {

   int countCardsByClientEmailAndType(String email, CardType cardType);
   Card findByNumber (String number);

}
