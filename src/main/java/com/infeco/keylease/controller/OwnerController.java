package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<Owner> getOwners() {
        return this.ownerService.getOwners();
    }

    @GetMapping("/owners/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<Owner> getOwnerById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.ownerService.getOwnerById(id));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/owners")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public Owner addOwner(@RequestBody Owner owner) {
        return ownerService.addOwner(owner);
    }

    @PutMapping("/owners/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<Owner> modifyOwner(@RequestBody Owner owner, @PathVariable UUID id) {
        try {
            Owner modifiedOwner = ownerService.modifyOwner(owner, id);
            return ResponseEntity.ok(modifiedOwner);
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
