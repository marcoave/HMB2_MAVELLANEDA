package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping ("/loans")
    public List<LoanDTO> getLoans() {

                return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());

    }

    @RequestMapping(path = "/loans", method = RequestMethod.POST)

        public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO,Authentication authentication) {
        Client clientDTO = clientRepository.findByEmail(authentication.getName());
        if (clientDTO == null) {
            return new ResponseEntity<>("No va", HttpStatus.FORBIDDEN);
        }

        //Verificaciones
        System.out.println(loanApplicationDTO.getLoanId());
        System.out.println(loanApplicationDTO.getAmount());
        System.out.println(loanApplicationDTO.getPayments());
        System.out.println(loanApplicationDTO.getAccountToNumber());

        //Utilizo nombre
        Loan typeLoan = loanRepository.findNameById(loanApplicationDTO.getLoanId());
        System.out.println(typeLoan.getName());



        //Verificar que los parámetros no estén vacíos


        Loan loanN=loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

        if ((loanN==null)||(loanApplicationDTO.getAmount()==null) ||(loanApplicationDTO.getPayments()==null)||loanApplicationDTO.getAccountToNumber().isBlank()) {
            return new ResponseEntity<>("Por favor completar todos los campos", HttpStatus.FORBIDDEN);
        }


        //Verificar que el prestamo exista

        if (!loanRepository.existsById(typeLoan.getId())) {
            return new ResponseEntity<>("El tipo de prestamo no existe", HttpStatus.FORBIDDEN);
       }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo

        if (!(((loanRepository.findByName(typeLoan.getName())).getMaxAmount()) >= loanApplicationDTO.getAmount())) {
            return new ResponseEntity<>("Debe ingresar un monto valido", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo

        if (!((loanRepository.findByName(typeLoan.getName()).getPayments()).contains(loanApplicationDTO.getPayments()))){
            return new ResponseEntity<>("La cuota ingresada no está disponible", HttpStatus.FORBIDDEN);
        }

        // Verificar que la cuenta de destino exista

        if((accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()) == null)){

            return new ResponseEntity<>("La cuenta ingresada no existe", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino pertenezca al cliente autenticado

        if (!clientDTO.getAccounts().contains(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()))){
            return new ResponseEntity<>("La cuenta no pertenece al usuario autenticado", HttpStatus.FORBIDDEN);

        }

        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo


        //Crear
        ClientLoan clientLoanNew =new ClientLoan();
        clientLoanNew.setClient(clientDTO);
        clientLoanNew.setLoan(loanN);
        clientLoanNew.setAmount(((loanApplicationDTO.getAmount()))*1.2);
        clientLoanNew.setPayments(loanApplicationDTO.getPayments());

        //Guardar prestamos

        clientLoanRepository.save(clientLoanNew);


        //Transaccion CREDIT

        Transaction transactionNew=new Transaction();
        transactionNew.setType(TransactionType.CREDITO);
        transactionNew.setAmount(loanApplicationDTO.getAmount());
        transactionNew.setDate(LocalDateTime.now());
        transactionNew.setDescription(loanN.getName() +" "+ loanApplicationDTO.getAccountToNumber()+"  loan approved");

        System.out.println(transactionNew.getAmount());

        //Agregar las transacciones

        (accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber())).addTransaction(transactionNew);

        System.out.println(accountRepository);

        //Guardar la transaction en la base de datos

        transactionRepository.save(transactionNew);

        System.out.println(transactionNew.getAmount());

        //Actualizar cuentas

        (accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber())).setBalance((accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber())).getBalance() + transactionNew.getAmount());

        accountRepository.save(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()));
        System.out.println(accountRepository.findByNumber(loanApplicationDTO.getAccountToNumber()).getBalance());
        System.out.println(accountRepository.findByNumber("VIN001").getBalance());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}