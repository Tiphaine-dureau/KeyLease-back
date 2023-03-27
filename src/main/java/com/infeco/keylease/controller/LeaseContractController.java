package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.models.PostLeaseContract;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.LeaseContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/lease-contracts")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<LeaseContract> addLeaseContract(@RequestBody PostLeaseContract leaseContract) {
        try {
            return ResponseEntity.ok(this.leaseContractService.addLeaseContract(leaseContract));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/lease-contracts/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<LeaseContract> modifyLeaseContract(@PathVariable UUID id, @RequestBody PostLeaseContract leaseContract) {
        try {
            return ResponseEntity.ok(this.leaseContractService.modifyLeaseContract(id, leaseContract));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/lease-contracts/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<Void> deleteOwner(@PathVariable UUID id) {
        try {
            leaseContractService.deleteLeaseContract(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
