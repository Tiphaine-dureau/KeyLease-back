package com.infeco.keylease.models;

import com.infeco.keylease.entity.LeaseContractEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    Set<Payment> payments;

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
        this.payments = leaseContractEntity.getPayments().stream().map(Payment::new).collect(Collectors.toSet());
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

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
