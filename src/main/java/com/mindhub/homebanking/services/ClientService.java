package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();

    void saveClient(Client client);

    Client findById (Long id);

    Client findByEmail (String email);

}
