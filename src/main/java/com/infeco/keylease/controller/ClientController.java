package com.infeco.keylease.controller;

import com.infeco.keylease.entity.ClientEntity;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<ClientEntity> getClients() {
        return this.clientService.getClients();
    }
}
