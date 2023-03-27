package com.infeco.keylease.models;

import com.infeco.keylease.entity.LeaseContractEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class LeaseContract {
    UUID id;
    BigDecimal rentAmount;
    BigDecimal rentCharges;
    BigDecimal requiredDeposit;
    BigDecimal paidDeposit;
    Date dateContractSignature;
    Owner owner;
    Tenant tenant;
    Property property;

    public LeaseContract() {
    }

    public LeaseContract(LeaseContractEntity leaseContractEntity) {
        this.id = leaseContractEntity.getId();
        this.rentAmount = leaseContractEntity.getRentAmount();
        this.rentCharges = leaseContractEntity.getRentCharges();
        this.requiredDeposit = leaseContractEntity.getRequiredDeposit();
        this.paidDeposit = leaseContractEntity.getPaidDeposit();
        this.dateContractSignature = leaseContractEntity.getDateContractSignature();
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

    public BigDecimal getRentCharges() {
        return rentCharges;
    }

    public void setRentCharges(BigDecimal rentCharges) {
        this.rentCharges = rentCharges;
    }

    public BigDecimal getRequiredDeposit() {
        return requiredDeposit;
    }

    public void setRequiredDeposit(BigDecimal requiredDeposit) {
        this.requiredDeposit = requiredDeposit;
    }

    public BigDecimal getPaidDeposit() {
        return paidDeposit;
    }

    public void setPaidDeposit(BigDecimal paidDeposit) {
        this.paidDeposit = paidDeposit;
    }

    public Date getDateContractSignature() {
        return dateContractSignature;
    }

    public void setDateContractSignature(Date dateContractSignature) {
        this.dateContractSignature = dateContractSignature;
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
