package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class TransactionController {
/*@Autowired
private TransactionRepository transactionRepository;
@Autowired
private ClientRepository clientRepository;
@Autowired
private AccountRepository accountRepository;*/

@Autowired
private AccountService accountService;
@Autowired
private ClientService clientService;
@Autowired
private TransactionsService transactionsService;

private ClientDTO clientDTO;


@Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)

    public ResponseEntity<String> createTransactions(Authentication authentication,@RequestParam Double amount,  @RequestParam String description, @RequestParam String accountFromNumber, @RequestParam String accountToNumber) {
        Client clientA=clientService.findByEmail(authentication.getName());
        //Client clientDTO = clientRepository.findByEmail(authentication.getName());

        if (authentication == null) {
            return new ResponseEntity<>("No va", HttpStatus.FORBIDDEN);
        }
        //Verificar que los parámetros no estén vacíos

        if (amount.isNaN() || description.isBlank() || accountFromNumber.isBlank()|| accountToNumber.isBlank()  ) {
            return new ResponseEntity<>("Por favor completar todos los campos", HttpStatus.FORBIDDEN);
        }

        //Verificar que los números de cuenta no sean iguales

        if (accountFromNumber.equals(accountToNumber)){
            return new ResponseEntity<>("Los numeros de cuenta son iguales", HttpStatus.FORBIDDEN);
        }

        //Verificar que exista la cuenta de origen

        //if (accountRepository.findByNumber(accountFromNumber) == null){
        if (accountService.findByNumber(accountFromNumber)==null){
            return new ResponseEntity<>("Los cuenta no existe", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de origen pertenezca al cliente autenticado

        //if (!clientDTO.getAccounts().contains(accountRepository.findByNumber(accountFromNumber))){
            if (!clientA.getAccounts().contains(accountService.findByNumber(accountFromNumber))){
            return new ResponseEntity<>("La cuenta no pertenece al usuario autenticado", HttpStatus.FORBIDDEN);

        }
        System.out.println(clientA.getAccounts());

        //Verificar que exista la cuenta de destino

        //if (accountRepository.findByNumber(accountToNumber) == null){
        Account accountTN=accountService.findByNumber(accountToNumber);

        //if (accountService.findByNumber(accountToNumber) == null){
        if (accountTN == null){
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de origen tenga el monto disponible.

        //if (accountRepository.findByNumber(accountFromNumber).getBalance() <= amount){
        Account accountFN=accountService.findByNumber(accountFromNumber);

        //if (accountService.findByNumber(accountFromNumber).getBalance() <= amount){
    if (accountFN.getBalance() <= amount){
            return new ResponseEntity<>("No posee el importe suficiente para la operación", HttpStatus.FORBIDDEN);
        }

        System.out.println(accountFN.getBalance());





        //Crear la transaccion

        //Transaccion DEBIT

        Transaction transaction1=new Transaction();
        transaction1.setType(TransactionType.DEBITO);
        transaction1.setAmount(-amount);
        transaction1.setDate(LocalDateTime.now());
        transaction1.setDescription(description+" "+accountFromNumber);

        //Transaccion CREDIT

        Transaction transaction2=new Transaction();
        transaction2.setType(TransactionType.CREDITO);
        transaction2.setAmount(amount);
        transaction2.setDate(LocalDateTime.now());
        transaction2.setDescription(description+" "+accountToNumber);


        //Agregar las transacciones

        //accountRepository.findByNumber(accountFromNumber).addTransaction(transaction1);
        //accountRepository.findByNumber(accountToNumber).addTransaction(transaction2);
        //accountService.findByNumber(accountFromNumber).addTransaction(transaction1);
        //accountService.findByNumber(accountToNumber).addTransaction(transaction2);

        accountFN.addTransaction(transaction1);
        accountTN.addTransaction(transaction2);

        //Guardar la transaction en la base de datos

        //transactionRepository.save(transaction1);
        //transactionRepository.save(transaction2);
        transactionsService.saveTransaction(transaction1);
        transactionsService.saveTransaction(transaction2);

        System.out.println(transaction1.getAmount());
        System.out.println(transaction2.getAmount());

        //Actualizar cuentas

        //accountRepository.findByNumber(accountFromNumber).setBalance(accountRepository.findByNumber(accountFromNumber).getBalance() + transaction1.getAmount());
        //accountRepository.findByNumber(accountToNumber).setBalance(accountRepository.findByNumber(accountToNumber).getBalance() + transaction2.getAmount());
        //accountService.findByNumber(accountFromNumber).setBalance(accountService.findByNumber(accountFromNumber).getBalance() + transaction1.getAmount());
        //accountService.findByNumber(accountToNumber).setBalance(accountService.findByNumber(accountToNumber).getBalance() + transaction2.getAmount());

        accountFN.setBalance(accountFN.getBalance() + transaction1.getAmount());
        accountTN.setBalance(accountTN.getBalance() + transaction2.getAmount());

        System.out.println(accountFN.getBalance());
        System.out.println(accountTN.getBalance());



        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //@RequestMapping("/clients/current/accounts")
    /*public AccountDTO getAccount(Authentication authentication,@RequestParam String accountFromNumber){
        return new AccountDTO(accountRepository.findByNumber(accountFromNumber));*/
   /* public ClientDTO getClient(Authentication authentication){
         return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }*/

        //System.out.println(AccountDTO);
   // }





}