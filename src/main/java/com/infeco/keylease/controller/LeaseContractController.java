package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.LeaseContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class LeaseContractController {
    private final LeaseContractService leaseContractService;

    public LeaseContractController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    @GetMapping("/lease-contracts")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<LeaseContract> getLeaseContracts() {
        return this.leaseContractService.getLeaseContracts();
    }

    @GetMapping("/lease-contracts/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<LeaseContract> getLeaseContractById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.leaseContractService.getLeaseContractById(id));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
