package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    private ClientDTO clientDTO;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")

    public List<AccountDTO> getAccountDTO() {

        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping("/clients/current/accounts")
    //public Account getAccount(Authentication authentication,@RequestParam String accountFromNumber){
       public List<AccountDTO> getAccountDTO(Authentication authentication) {
        Client clientDTO = clientRepository.findByEmail(authentication.getName());
        return clientDTO.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        //return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    //}

        //return accountRepository.findByNumber(accountFromNumber);

        //System.out.println(AccountDTO);
    }

    //------------
    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<String> createAccount(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>("No va", HttpStatus.FORBIDDEN);
        }
            Client clientDTO = clientRepository.findByEmail(authentication.getName());

            if (clientDTO.getAccounts().size() >= 3) {
                return new ResponseEntity<>("Máximo tres cuentas", HttpStatus.FORBIDDEN);
            }
            // Generar un número de cuenta aleatorio
            String accountNumber = generateAccountNumber();

            //Verificar que no exista el numero de cuenta
            if (accountRepository.findByNumber(accountNumber)!=null) {
            return new ResponseEntity<>("Numero de cuenta existente", HttpStatus.FORBIDDEN);
            }

            //String accountNumber = newAccountNumber1();

            // Crear la nueva cuenta
            Account newAccount = new Account();
            newAccount.setNumber(accountNumber);
            newAccount.setBalance(0.0);
            newAccount.setCreationDate(LocalDate.now());
            //newAccount.setClient(client);
            clientDTO.addAccount(newAccount);
            System.out.println(newAccount);

            // Guardar la cuenta en el repositorio
            accountRepository.save(newAccount);

            System.out.println(newAccount);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        private String generateAccountNumber () {

            return "VIN-" + getRandomNumber(1, 9999999);

        }

        public int getRandomNumber ( int min, int max){
            return (int) ((Math.random() * (max - min)) + min);

        }



//-------
    /*@RequestMapping("/clients/current/accounts")
    public ResponseEntity<Object> showAccount(Authentication authentication) {

        Client clientDTO = clientRepository.findByEmail(authentication.getName());

        return new Set<AccountDTO>(clientDTO.getAccounts());

        }*/



    }
