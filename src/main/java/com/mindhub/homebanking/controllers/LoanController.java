package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;

import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class LoanController {
    //@Autowired
    //private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    /*@Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;*/
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionsService transactionsService;

    @RequestMapping ("/loans")
    public List<LoanDTO> getLoans() {
                return loanService.getLoansDTO();
                //return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());

    }
@Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)

        public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO,Authentication authentication) {
        Client clientA=clientService.findByEmail(authentication.getName());
        //Client clientDTO = clientRepository.findByEmail(authentication.getName());
        if (clientA == null) {
            return new ResponseEntity<>("Intentar autenticación nuevamente", HttpStatus.FORBIDDEN);
        }

        //Datos recibidos
        System.out.println(loanApplicationDTO.getLoanId());
        System.out.println(loanApplicationDTO.getAmount());
        System.out.println(loanApplicationDTO.getPayments());
        System.out.println(loanApplicationDTO.getAccountToNumber());


        //Verificar que los parámetros no estén vacíos

        Loan loanN=loanService.findById(loanApplicationDTO.getLoanId());
        //Loan loanN=loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        //if ((loanN==null)||(loanApplicationDTO.getAmount()==null) ||(loanApplicationDTO.getPayments()==null)||loanApplicationDTO.getAccountToNumber().isBlank()) {
        if ((loanN==null)||(loanApplicationDTO.getAmount()==null) ||(loanApplicationDTO.getPayments()==null)||loanApplicationDTO.getAccountToNumber().isBlank()) {
            return new ResponseEntity<>("Por favor completar todos los campos", HttpStatus.FORBIDDEN);
        }

        //Verificar que el prestamo exista

        //if (!loanRepository.existsById(loanN.getId())) {

            //if (!loanService.existsById(loanN.getId())) {
                if ((loanN.getId())==null) {
            return new ResponseEntity<>("El tipo de prestamo no existe", HttpStatus.FORBIDDEN);
       }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo

        if (!(loanN.getMaxAmount()>=loanApplicationDTO.getAmount())){
            return new ResponseEntity<>("Debe ingresar un monto valido", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo

        if(!(loanN.getPayments().contains(loanApplicationDTO.getPayments()))){
            return new ResponseEntity<>("La cuota ingresada no está disponible", HttpStatus.FORBIDDEN);
        }

        // Verificar que la cuenta de destino exista

        //if((accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()) == null)){
        Account accountToN=accountService.findByNumber(loanApplicationDTO.getAccountToNumber());
        //if((accountService.findByNumber(loanApplicationDTO.getAccountToNumber()) == null)){
        if((accountToN == null)){
            return new ResponseEntity<>("La cuenta ingresada no existe", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino pertenezca al cliente autenticado

        //if (!clientA.getAccounts().contains(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()))){
        if (!clientA.getAccounts().contains(accountToN)){
            return new ResponseEntity<>("La cuenta no pertenece al usuario autenticado(La cuenta no te pertenece)", HttpStatus.FORBIDDEN);

        }

        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo


        //Crear
        ClientLoan clientLoanNew =new ClientLoan();
        clientLoanNew.setClient(clientA);
        clientLoanNew.setLoan(loanN);
        clientLoanNew.setAmount(((loanApplicationDTO.getAmount()))*1.2);
        clientLoanNew.setPayments(loanApplicationDTO.getPayments());

        //Guardar prestamos

        //clientLoanRepository.save(clientLoanNew);
        clientLoanService.saveClientLoan(clientLoanNew);

        //Transaccion CREDIT

        Transaction transactionNew=new Transaction();
        transactionNew.setType(TransactionType.CREDITO);
        transactionNew.setAmount(loanApplicationDTO.getAmount());
        transactionNew.setDate(LocalDateTime.now());
        transactionNew.setDescription(loanN.getName() +" "+ loanApplicationDTO.getAccountToNumber()+"  loan approved");

        System.out.println(transactionNew.getAmount());

        //Agregar las transacciones

        //(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber())).addTransaction(transactionNew);
        //(accountService.findByNumber(loanApplicationDTO.getAccountToNumber())).addTransaction(transactionNew);
        (accountToN).addTransaction(transactionNew);
        System.out.println(accountService);

        //Guardar la transaction en la base de datos

        //transactionRepository.save(transactionNew);
        transactionsService.saveTransaction(transactionNew);

        System.out.println(transactionNew.getAmount());

        //Actualizar cuentas

        //(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber())).setBalance((accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber())).getBalance() + transactionNew.getAmount());
        //(accountService.findByNumber(loanApplicationDTO.getAccountToNumber())).setBalance((accountService.findByNumber(loanApplicationDTO.getAccountToNumber())).getBalance() + transactionNew.getAmount());

        (accountToN).setBalance((accountToN).getBalance() + transactionNew.getAmount());

        //accountRepository.save(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()));
        //accountService.saveAccount(accountService.findByNumber(loanApplicationDTO.getAccountToNumber()));
        accountService.saveAccount(accountToN);

        System.out.println(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()).getBalance());
        System.out.println(accountRepository.findByNumber("VIN001").getBalance());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}