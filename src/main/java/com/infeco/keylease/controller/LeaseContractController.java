package com.infeco.keylease.controller;

import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.LeaseContractService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
