package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan,Long> {
    //Optional<Loan> findById(Long id);
    //Loan findById (String id);
    Loan findNameById(Long id);
    //Loan findNameById(String id);
    Loan findByName(String name);
}
