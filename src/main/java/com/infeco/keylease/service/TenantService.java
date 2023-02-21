package com.infeco.keylease.service;

import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getTenants() {
        return this.tenantRepository.findAll().stream().map(Tenant::new).collect(Collectors.toList());
    }

    public Tenant addTenant(Tenant tenant) {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName(tenant.getFirstName());
        tenantEntity.setLastName(tenant.getLastName());
        tenantEntity.setEmail(tenant.getEmail());
        tenantEntity.setPhoneNumber(tenant.getPhoneNumber());
        tenantEntity.setPartnerFirstName(tenant.getPartnerFirstName());
        tenantEntity.setPartnerLastName(tenant.getPartnerLastName());
        tenantEntity.setPartnerPhoneNumber(tenant.getPartnerPhoneNumber());
        TenantEntity savedTenantEntity = this.tenantRepository.save(tenantEntity);
        Tenant savedTenant = new Tenant();
        savedTenant.setFirstName(savedTenantEntity.getFirstName());
        savedTenant.setLastName(savedTenantEntity.getLastName());
        savedTenant.setEmail(savedTenantEntity.getEmail());
        savedTenant.setPhoneNumber(savedTenantEntity.getPhoneNumber());
        savedTenant.setPartnerFirstName(savedTenantEntity.getPartnerFirstName());
        savedTenant.setPartnerLastName(savedTenantEntity.getPartnerLastName());
        savedTenant.setPartnerPhoneNumber(savedTenantEntity.getPartnerPhoneNumber());
        return savedTenant;
    }
}