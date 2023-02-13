package com.infeco.keylease.controller;

import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.TenantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/tenants")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<Tenant> getTenants() {
        return this.tenantService.getTenants();
    }
/*
    @PostMapping("/tenants")
    public void createTenant(
            @RequestBody AuthenticationRequest request
    ){
        tenantService.createTenant(t);
    }*/
}
