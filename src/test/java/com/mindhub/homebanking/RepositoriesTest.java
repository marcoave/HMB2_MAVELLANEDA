package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;




import java.util.List;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)

public class RepositoriesTest {
@Autowired
LoanRepository loanRepository;
@Autowired
    AccountRepository accountRepository;
@Autowired
    ClientRepository clientRepository;
@Autowired
    TransactionRepository transactionRepository;
@Test
    public void existLoans(){
    List<Loan>loans=loanRepository.findAll();

    assertThat(loans,hasItem(hasProperty("name",is("Hipotecario"))));
}
    @Test
    public void existIDLoans(){

        List<Loan>loans=loanRepository.findAll();

        assertThat(loans,hasItem(hasProperty("id",is(9L))));
    }

    @Test
    public void existClientsN(){
        List<Client>clients=clientRepository.findAll();

        assertThat(clients,hasItem(hasProperty("firstName",is("Melba"))));
    }
    @Test
    public void existClientsL(){
        List<Client>clients=clientRepository.findAll();

        assertThat(clients,hasItem(hasProperty("lastName",is("Morel"))));
    }
    @Test
    public void existClientsE(){
        List<Client>clients=clientRepository.findAll();

        assertThat(clients,hasItem(hasProperty("email",is("MM@gmail.com"))));
    }
    @Test
    public void existAccounts(){

        List<Account>accounts=accountRepository.findAll();

        assertThat(accounts,hasItem(hasProperty("number",is("VIN001"))));
    }
    @Test
    public void existAccountsE(){

        List<Account>accounts=accountRepository.findAll();
    //Probando falla
        assertThat(accounts,hasItem(hasProperty("number",is("VIN00X"))));
    }

    @Test
    public void existTransactionsC(){

        List<Transaction>transactions=transactionRepository.findAll();

        assertThat(transactions,hasItem(hasProperty("type",is(TransactionType.CREDITO))));
    }

    @Test
    public void existTransactionsD(){

        List<Transaction>transactions=transactionRepository.findAll();

        assertThat(transactions,hasItem(hasProperty("type",is(TransactionType.DEBITO))));
    }


}
