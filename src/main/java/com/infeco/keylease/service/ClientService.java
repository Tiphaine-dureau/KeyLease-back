package com.infeco.keylease.service;

import com.infeco.keylease.entity.ClientEntity;
import com.infeco.keylease.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientEntity> getClients() {
        return this.clientRepository.findAll();
    }
}
