package com.infeco.keylease.controller;

import com.infeco.keylease.models.Owner;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.OwnerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<Owner> getOwners(){
        return this.ownerService.getOwners();
    }

    @PostMapping("/owners")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public Owner addOwner(@RequestBody Owner owner){
        return ownerService.addOwner(owner);
    }
}
