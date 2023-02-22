package com.infeco.keylease.service;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final AddressRepository addressRepository;

    public TenantService(TenantRepository tenantRepository, AddressRepository addressRepository) {
        this.tenantRepository = tenantRepository;
        this.addressRepository = addressRepository;
    }

    public List<Tenant> getTenants() {
        return this.tenantRepository.findAll().stream().map(Tenant::new).collect(Collectors.toList());
    }

    public Tenant addTenant(Tenant tenant) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(tenant.getAddress().getStreet());
        addressEntity.setAdditionalAddress(tenant.getAddress().getAdditionalAddress());
        addressEntity.setZipCode(tenant.getAddress().getZipCode());
        addressEntity.setTown(tenant.getAddress().getTown());
        AddressEntity savedAddressEntity = this.addressRepository.save(addressEntity);
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName(tenant.getFirstName());
        tenantEntity.setLastName(tenant.getLastName());
        tenantEntity.setEmail(tenant.getEmail());
        tenantEntity.setPhoneNumber(tenant.getPhoneNumber());
        tenantEntity.setPartnerFirstName(tenant.getPartnerFirstName());
        tenantEntity.setPartnerLastName(tenant.getPartnerLastName());
        tenantEntity.setPartnerPhoneNumber(tenant.getPartnerPhoneNumber());
        tenantEntity.setAddress(savedAddressEntity);
        TenantEntity savedTenantEntity = this.tenantRepository.save(tenantEntity);

        Tenant savedTenant = new Tenant();
        savedTenant.setFirstName(savedTenantEntity.getFirstName());
        savedTenant.setLastName(savedTenantEntity.getLastName());
        savedTenant.setEmail(savedTenantEntity.getEmail());
        savedTenant.setPhoneNumber(savedTenantEntity.getPhoneNumber());
        savedTenant.setPartnerFirstName(savedTenantEntity.getPartnerFirstName());
        savedTenant.setPartnerLastName(savedTenantEntity.getPartnerLastName());
        savedTenant.setPartnerPhoneNumber(savedTenantEntity.getPartnerPhoneNumber());
        Address savedAddress = new Address();
        savedAddress.setStreet(savedTenantEntity.getAddress().getStreet());
        savedAddress.setAdditionalAddress(savedTenantEntity.getAddress().getAdditionalAddress());
        savedAddress.setZipCode(savedTenantEntity.getAddress().getZipCode());
        savedAddress.setTown(savedTenantEntity.getAddress().getTown());
        savedTenant.setAddress(savedAddress);
        return savedTenant;
    }
}
