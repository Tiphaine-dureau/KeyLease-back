package com.infeco.keylease.service;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.LeaseContractEntity;
import com.infeco.keylease.entity.PaymentEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final AddressRepository addressRepository;
    private final LeaseContractRepository leaseContractRepository;

    public TenantService(TenantRepository tenantRepository, AddressRepository addressRepository, LeaseContractRepository leaseContractRepository) {
        this.tenantRepository = tenantRepository;
        this.addressRepository = addressRepository;
        this.leaseContractRepository = leaseContractRepository;
    }

    public List<Tenant> getTenants() {
        return this.tenantRepository.findAll().stream().map(Tenant::new).collect(Collectors.toList());
    }

    public Tenant addTenant(Tenant tenant) {
        AddressEntity addressEntity = new AddressEntity();
        addressToEntity(tenant.getAddress(), addressEntity);
        AddressEntity savedAddressEntity = this.addressRepository.save(addressEntity);
        TenantEntity tenantEntity = new TenantEntity();
        tenantToEntity(tenant, tenantEntity);
        tenantEntity.setAddress(savedAddressEntity);
        TenantEntity savedTenantEntity = this.tenantRepository.save(tenantEntity);
        return entityToTenant(savedTenantEntity);
    }

    public Tenant modifyTenant(Tenant tenant, UUID id) throws NotFoundEntity {
        Optional<TenantEntity> optionalTenantEntity = this.tenantRepository.findById(id);
        if (optionalTenantEntity.isPresent()) {
            TenantEntity tenantEntity = optionalTenantEntity.get();
            tenantToEntity(tenant, tenantEntity);
            addressToEntity(tenant.getAddress(), tenantEntity.getAddress());
            TenantEntity savedEntity = this.tenantRepository.save(tenantEntity);
            return entityToTenant(savedEntity);
        } else {
            throw new NotFoundEntity();
        }
    }

    public Tenant getTenantById(UUID id) throws NotFoundEntity {
        Optional<TenantEntity> optionalTenantEntity = this.tenantRepository.findById(id);
        if (optionalTenantEntity.isPresent()) {
            return entityToTenant(optionalTenantEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    public void deleteTenant(UUID id) throws NotFoundEntity {
        Optional<TenantEntity> optionalTenantEntity = this.tenantRepository.findById(id);
        if (optionalTenantEntity.isPresent()) {
            this.tenantRepository.delete(optionalTenantEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    private void tenantToEntity(Tenant tenant, TenantEntity tenantEntity) {
        tenantEntity.setFirstName(tenant.getFirstName());
        tenantEntity.setLastName(tenant.getLastName());
        tenantEntity.setBirthday(tenant.getBirthday());
        tenantEntity.setEmail(tenant.getEmail());
        tenantEntity.setPhoneNumber(tenant.getPhoneNumber());
        tenantEntity.setPartnerFirstName(tenant.getPartnerFirstName());
        tenantEntity.setPartnerLastName(tenant.getPartnerLastName());
        tenantEntity.setPartnerPhoneNumber(tenant.getPartnerPhoneNumber());
    }

    private void addressToEntity(Address address, AddressEntity addressEntity) {
        addressEntity.setStreet(address.getStreet());
        addressEntity.setAdditionalAddress(address.getAdditionalAddress());
        addressEntity.setZipCode(address.getZipCode());
        addressEntity.setTown(address.getTown());
    }

    private Tenant entityToTenant(TenantEntity tenantEntity) {
        Tenant tenant = new Tenant(tenantEntity);
        setBalance(tenant, tenantEntity);
        return tenant;
    }

    /**
     * Calcule le montant des loyers payés
     *
     * @param tenant
     * @param tenantEntity
     */
    private void setBalance(Tenant tenant, TenantEntity tenantEntity) {
        List<LeaseContractEntity> leaseContractEntityList = this.leaseContractRepository.getAllByTenantId(tenantEntity.getId());
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal expectedRentAmount = BigDecimal.ZERO;
        for (LeaseContractEntity leaseContractEntity : leaseContractEntityList) {
            List<PaymentEntity> paymentEntityList = new ArrayList<>(leaseContractEntity.getPayments());
            expectedRentAmount = expectedRentAmount.add(expectedRentAmount(leaseContractEntity));
            for (PaymentEntity paymentEntity : paymentEntityList) {
                balance = balance.add(paymentEntity.getPaidRent());
            }
        }
        tenant.setBalance(balance);
        tenant.setExpectedRentAmount(expectedRentAmount);
    }

    private BigDecimal expectedRentAmount(LeaseContractEntity leaseContractEntity) {
        BigDecimal expectedRentAmount = BigDecimal.ZERO;
        if (leaseContractEntity.getPayments().size() > 0) {
            expectedRentAmount = leaseContractEntity.getRentAmount().multiply(BigDecimal.valueOf(leaseContractEntity.getPayments().size()));
        }
        return expectedRentAmount;
    }
}
