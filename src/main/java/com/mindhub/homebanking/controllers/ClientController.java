package com.mindhub.homebanking.controllers;//package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {

        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
//---

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register (
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
            @RequestParam String password) {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }


        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client1=clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));


        //Client newClient= new Client();
        // Generar un número de cuenta aleatorio
        String accountNumber = generateAccountNumber();

        // Crear la nueva cuenta
        Account newAccount = new Account();
        newAccount.setNumber(accountNumber);
        newAccount.setBalance(0.0);
        newAccount.setCreationDate(LocalDate.now());
        client1.addAccount(newAccount);
        System.out.println(newAccount);

     // Guardar la cuenta en el repositorio
        accountRepository.save(newAccount);

        System.out.println(newAccount);



        return new ResponseEntity<>(HttpStatus.CREATED);
    }

@RequestMapping("/clients/current")

    public ClientDTO getClient(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
}


    private String generateAccountNumber(){

        return "VIN-"+ getRandomNumber(1,9999999);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);

    }





}