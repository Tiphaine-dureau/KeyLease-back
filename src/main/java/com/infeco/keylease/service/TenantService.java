package com.infeco.keylease.service;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
        AddressEntity addressEntity = addressToEntity(tenant.getAddress(), new AddressEntity());
        AddressEntity savedAddressEntity = this.addressRepository.save(addressEntity);
        TenantEntity tenantEntity = tenantToEntity(tenant, new TenantEntity());
        tenantEntity.setAddress(savedAddressEntity);
        TenantEntity savedTenantEntity = this.tenantRepository.save(tenantEntity);
        return entityToTenant(savedTenantEntity);
    }

    public Tenant modifyTenant(Tenant tenant, UUID id) {
        return new Tenant();
    }

    private TenantEntity tenantToEntity(Tenant tenant, TenantEntity tenantEntity) {
        tenantEntity.setFirstName(tenant.getFirstName());
        tenantEntity.setLastName(tenant.getLastName());
        tenantEntity.setEmail(tenant.getEmail());
        tenantEntity.setPhoneNumber(tenant.getPhoneNumber());
        tenantEntity.setPartnerFirstName(tenant.getPartnerFirstName());
        tenantEntity.setPartnerLastName(tenant.getPartnerLastName());
        tenantEntity.setPartnerPhoneNumber(tenant.getPartnerPhoneNumber());
        return tenantEntity;
    }

    private AddressEntity addressToEntity(Address address, AddressEntity addressEntity) {
        addressEntity.setStreet(address.getStreet());
        addressEntity.setAdditionalAddress(address.getAdditionalAddress());
        addressEntity.setZipCode(address.getZipCode());
        addressEntity.setTown(address.getTown());
        return addressEntity;
    }

    private Tenant entityToTenant(TenantEntity tenantEntity) {
        Tenant tenant = new Tenant();
        Address address = new Address();
        tenant.setId(tenantEntity.getId());
        tenant.setFirstName(tenantEntity.getFirstName());
        tenant.setLastName(tenantEntity.getLastName());
        tenant.setEmail(tenantEntity.getEmail());
        tenant.setPhoneNumber(tenantEntity.getPhoneNumber());
        tenant.setPartnerFirstName(tenantEntity.getPartnerFirstName());
        tenant.setPartnerLastName(tenantEntity.getPartnerLastName());
        tenant.setPartnerPhoneNumber(tenantEntity.getPartnerPhoneNumber());
        address.setStreet(tenantEntity.getAddress().getStreet());
        address.setAdditionalAddress(tenantEntity.getAddress().getAdditionalAddress());
        address.setZipCode(tenantEntity.getAddress().getZipCode());
        address.setTown(tenantEntity.getAddress().getTown());
        tenant.setAddress(address);
        return tenant;
    }
}
