package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.image.PixelGrabber;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/tenants")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public Tenant addTenant(@RequestBody Tenant tenant) {
        return tenantService.addTenant(tenant);
    }

    @PutMapping("/tenants/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<Tenant> modifyTenant(@RequestBody Tenant tenant, @PathVariable UUID id) {
        try {
            Tenant modifiedTenant = tenantService.modifyTenant(tenant, id);
            return ResponseEntity.ok(modifiedTenant);
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
