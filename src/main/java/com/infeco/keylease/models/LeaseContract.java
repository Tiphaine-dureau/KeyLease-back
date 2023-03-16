package com.infeco.keylease.models;

import com.infeco.keylease.entity.LeaseContractEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class LeaseContract {
    UUID id;
    BigDecimal rentAmount;
    Owner owner;
    Tenant tenant;
    Property property;

    public LeaseContract() {
    }

    public LeaseContract(LeaseContractEntity leaseContractEntity) {
        this.id = leaseContractEntity.getId();
        this.rentAmount = leaseContractEntity.getRentAmount();
        this.owner = new Owner(leaseContractEntity.getOwner());
        this.tenant = new Tenant(leaseContractEntity.getTenant());
        this.property = new Property(leaseContractEntity.getProperty());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
